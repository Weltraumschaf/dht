/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.dht.cmd;

import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.commons.token.TokenType;
import de.weltraumschaf.dht.msg.Message;
import de.weltraumschaf.dht.msg.MessageBox;
import de.weltraumschaf.dht.msg.Messaging;
import de.weltraumschaf.dht.shell.CommandArgumentExcpetion;
import de.weltraumschaf.dht.shell.MainCommand;
import static de.weltraumschaf.dht.shell.MainCommand.INBOX;
import de.weltraumschaf.dht.shell.CommandRuntimeException;
import de.weltraumschaf.dht.shell.SubCommand;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;

/**
 * Executes `inbox` command.
 *
 * Sub commands: - remve ID - show ID - answer ID
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Inbox extends BaseCommand {

    private static final int FIRST_COLUMN_PAD = 4;
    private static final int SECOND_COLUMN_PAD = 12;
    private static final int THIRD_COLUMN_PAD = 20;

    @Override
    public Descriptor getDescriptor() {
        return new BaseDescriptor() {

            @Override
            public MainCommand getCommand() {
                return INBOX;
            }

            @Override
            public String getUsage() {
                return INBOX.toString();
            }

            @Override
            public String getHelpDescription() {
                return "Give sacces to the message inbox.";
            }
        };
    }

    @Override
    public void execute() {
        if (getSubCommand() == SubCommand.NONE) {
            showList();
            return;
        }

        dispatchSubCommand();
    }

    private boolean showList() {
        println(String.format("Your incomming messages for %s:", formatLocalAddress()));
        println();
        final MessageBox inbox = getInbox();
        if (inbox.isEmpty()) {
            println("Your inbox is empty.");
            println();
            return true;
        }
        print(StringUtils.rightPad("Id", FIRST_COLUMN_PAD));
        print(StringUtils.rightPad(String.format("Unread (%d)", inbox.countUnread()), SECOND_COLUMN_PAD));
        print(StringUtils.rightPad("From", THIRD_COLUMN_PAD));
        println("Body");
        final Collection<Message> messages = inbox.getAll();
        int id = 0;
        for (final Message message : messages) {
            println(formatMessage(message, id++));
        }
        println();
        return false;
    }

    private String formatMessage(final Message message, final int id) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(formatId(id));
        buffer.append(formatUnread(message));
        buffer.append(formatFrom(message));
        buffer.append(message.getBody());
        return buffer.toString();
    }

    private Object formatId(final int id) {
        return StringUtils.leftPad(String.valueOf(id) + " ", FIRST_COLUMN_PAD);
    }

    private String formatUnread(final Message message) {
        final String unread = message.isUnread() ? "yes" : "no";
        return StringUtils.rightPad(unread, SECOND_COLUMN_PAD);
    }

    private String formatFrom(Message message) {
        return StringUtils.rightPad(message.getFrom().toString(), THIRD_COLUMN_PAD);
    }

    private void dispatchSubCommand() {
        switch ((SubCommand) getSubCommand()) {
            case INBOX_ANSWER:
                answerMessage();
                break;
            case INBOX_REMOVE:
                removeMessage();
                break;
            case INBOX_SHOW:
                showMessage();
                break;
            default:
                throw new CommandArgumentExcpetion("Unsupport subcommand >" + getSubCommand().toString() + "<");
        }
    }

    private void answerMessage() {
        final Token idToken = getArguments().get(0);
        final int id = validateId(idToken);
        final Message message = getInbox().get(id);
        final Token messageBodyToken = getArguments().get(1);
        final Message answer = Messaging.newTextMessage(
                newLocalAddress(),
                message.getFrom(),
                messageBodyToken.asString());

        try {
            Messaging.newSender().send(answer);
        } catch (IOException ex) {
            throw new CommandRuntimeException(String.format("Can't send message to %s!", message.getFrom()), ex);
        }

        answer.markAsRead();
        getApplicationContext().getOutbox().put(answer);
    }

    private void removeMessage() {
        final Token idToken = getArguments().get(0);
        final int id = validateId(idToken);
        getInbox().remove(id);
        println(String.format("Message with id %d removed.", id));
        println();
    }

    private void showMessage() {
        final Token idToken = getArguments().get(0);
        final int id = validateId(idToken);
        final Message message = getInbox().get(id);
        println(String.format("Id: %d", id));
        println(String.format("From: %s", message.getFrom().toString()));
        println(String.format("Body: %s", message.getBody()));
        println();
        message.markAsRead();
    }

    private MessageBox getInbox() {
        return getApplicationContext().getInbox();
    }

    private int validateId(final Token idToken) {
        if (idToken.getType() != TokenType.INTEGER) {
            throw new CommandArgumentExcpetion("Id must be a number!");
        }

        final Integer id = idToken.asInteger();

        if (id < 0) {
            throw new CommandArgumentExcpetion("Id must not be negative!");
        }

        if (id > getInbox().count() - 1) {
            throw new CommandArgumentExcpetion(String.format("Id must not be less than %d!", getInbox().count()));
        }

        return id;
    }

}

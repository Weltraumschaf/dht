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

import de.weltraumschaf.dht.msg.Message;
import de.weltraumschaf.dht.msg.MessageBox;
import de.weltraumschaf.dht.shell.CommandMainType;
import static de.weltraumschaf.dht.shell.CommandMainType.INBOX;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;

/**
 * Executes `inbox` command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Inbox extends BaseCommand {

    public static final int SECOND_COLUMN_PAD = 30;
    private static final int FIRST_COLUMN_PAD = 14;

    @Override
    public void execute() {
        println(String.format("Your incomming messages for %s:", formatListenedAddress()));
        println();

        final MessageBox inbox = getApplication().getInbox();

        if (inbox.isEmpty()) {
            println("Your inbox is empty.");
            println();
            return;
        }

        print(StringUtils.rightPad(String.format("Unread (%d)", inbox.countUnread()), FIRST_COLUMN_PAD));
        print(StringUtils.rightPad("From", SECOND_COLUMN_PAD));
        println("Body");


        final Collection<Message> messages = inbox.getAll();

        for (final Message message : messages) {
            println(formatMessage(message));
        }

        println();
    }

    private String formatMessage(final Message message) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(formatUnread(message));
        buffer.append(formatFrom(message));
        buffer.append(message.getBody());
        return buffer.toString();
    }

    private String formatUnread(final Message message) {
        final String unread = message.isUnread() ? "yes" : "no";
        return StringUtils.rightPad(unread, FIRST_COLUMN_PAD);
    }

    private String formatFrom(Message message) {
        return StringUtils.rightPad(message.getFrom().toString(), SECOND_COLUMN_PAD);
    }

    @Override
    public Descriptor getDescriptor() {
        return new BaseDescriptor() {

            @Override
            public CommandMainType getCommand() {
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

}

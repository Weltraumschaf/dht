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

import de.weltraumschaf.dht.msg.MessageBox;
import de.weltraumschaf.dht.server.Server;
import de.weltraumschaf.dht.shell.CommandMainType;
import static de.weltraumschaf.dht.shell.CommandMainType.STATUS;
import org.apache.commons.lang3.StringUtils;

/**
 * Executes `status` command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Status extends BaseCommand {

    private static final String LEFT_PAD = "  ";
    private static final int RIGHT_PAD = 10;

    @Override
    public Descriptor getDescriptor() {
        return new BaseDescriptor() {

            @Override
            public CommandMainType getCommand() {
                return STATUS;
            }

            @Override
            public String getUsage() {
                return STATUS.toString();
            }

            @Override
            public String getHelpDescription() {
                return "Show application status information.";
            }
        };
    }

    @Override
    public void execute() {
        final StringBuilder buffer = new StringBuilder();
        nodeInfo(buffer);
        serverInfo(buffer);
        messageInfos(buffer);
        println(buffer.toString());
    }

    private String rightPad(final String input) {
        return StringUtils.rightPad(input, RIGHT_PAD);
    }

    private String getNodeId() {
        return getApplicationContext().getNodeId().asString();
    }

    private Server getServer() {
        return getApplicationContext().getServer();
    }

    private MessageBox getOutbox() {
        return getApplicationContext().getOutbox();
    }

    private MessageBox getInbox() {
        return getApplicationContext().getInbox();
    }

    private void nodeInfo(final StringBuilder buffer) {
        buffer.append("Node:").append(nl());
        buffer.append(LEFT_PAD).append(rightPad("Id:")).append(getNodeId()).append(nl());
    }

    private void serverInfo(final StringBuilder buffer) {
        buffer.append("Server:").append(nl());
        buffer.append(LEFT_PAD).append(rightPad("State:")).append(getServer().getState()).append(nl());
        buffer.append(LEFT_PAD).append(rightPad("Address:")).append(formatLocalAddress()).append(nl());
        buffer.append(LEFT_PAD).append(rightPad("Queue:")).append(getServer().countQueue()).append(nl());
    }

    private void messageInfos(final StringBuilder buffer) {
        buffer.append("Message:").append(nl());
        buffer.append(LEFT_PAD).append(rightPad("Inbox:")).append(formatInbox()).append(nl());
        buffer.append(LEFT_PAD).append(rightPad("Outbox:")).append(getOutbox().count()).append(nl());
    }

    private String formatInbox() {
        final MessageBox inbox = getInbox();
        return String.format("%d (%d unread)", inbox.count(), inbox.countUnread());
    }

}

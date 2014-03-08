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
package de.weltraumschaf.dht.msg;

/**
 * Represents a message send over the network.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Message {

    /**
     * From where the message came.
     *
     * @return never {@code null}
     */
    MessageAddress getFrom();

    /**
     * The message receiver.
     *
     * @return never {@code null}
     */
    MessageAddress getTo();

    /**
     * Get the message body.
     *
     * @return never {@code null}
     */
    String getBody();

    /**
     * Mark the message as read.
     *
     * Subsequent calls has no effect.
     */
    void markAsRead();

    /**
     * Whether the message is unread or not.
     *
     * This method must return {@code true} until first invocation of {@link #markAsRead()}.
     *
     * @return {@code true} if message is unread, else {@code false}
     */
    boolean isUnread();

    MessageType getType();

}

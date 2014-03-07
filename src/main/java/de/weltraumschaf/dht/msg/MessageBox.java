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

import java.util.Collection;

/**
 * This type defines a post box like container for messages.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface MessageBox {

    /**
     * Add a message in to the box.
     *
     * @param msg must not be {@code null}
     */
    void put(Message msg);
    /**
     * Removes a message in to the box.
     *
     * @param msg must not be {@code null}
     */
    void remove(Message msg);
    /**
     * Gets the number of unread messages.
     *
     * A message remains unread until you {@link Message#markAsRead() mark it read}.
     *
     * @return never negative
     */
    int countUnread();
    /**
     * Gets the total number of messages in the box.
     *
     * @return never negative
     */
    int count();
    /**
     * Get a immutable collection of all messages in the box.
     *
     * @return never {@code null}
     */
    Collection<Message> getAll();
    /**
     * Whether there are messages or not.
     *
     * @return {@code true} if no messages were put in to the box, else {@code false}
     */
    boolean isEmpty();
}

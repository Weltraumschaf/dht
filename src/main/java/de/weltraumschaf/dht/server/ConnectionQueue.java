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

package de.weltraumschaf.dht.server;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Queues the incoming connections.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class ConnectionQueue<T> {

    /**
     * Holds the connections.
     */
    private final Queue<T> sockets = new ConcurrentLinkedQueue<T>();

    /**
     * Put a connection at the tail of the queue.
     *
     * @param socket saved connection
     */
    public void put(final T socket) {
        sockets.offer(socket);
    }

    /**
     * Get most head connection.
     *
     * @return head connection
     */
    public T get() {
        return sockets.poll();
    }

    /**
     * Whether the queue is empty or not.
     *
     * @return {@link true} if empty, else {@link false}
     */
    public boolean isEmpty() {
        return sockets.isEmpty();
    }

    /**
     * Size of connections.
     *
     * @return
     */
    public int size() {
        return sockets.size();
    }

    /**
     * Removes all queued connections.
     */
    public void clear() {
        sockets.clear();
    }

}

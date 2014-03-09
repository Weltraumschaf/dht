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
package de.weltraumschaf.dht;

import de.weltraumschaf.dht.id.NodeId;
import java.math.BigInteger;

/**
 * http://www.linuxjournal.com/article/6797
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DhtService {

    private static final int KEY_SIZE = 160;

    /**
     * This is a clockwise ring distance function.
     *
     * It depends on a globally defined k, the key size. The largest possible node id is 2**k.
     *
     * @param a
     * @param b
     * @return
     */
    public static BigInteger distance(final NodeId a, final NodeId b) {
        return a.asInteger().xor(b.asInteger());
    }

    /**
     * From the start node, find the node responsible for the target key.
     *
     * @param <T>
     * @param start
     * @param key
     * @return
     */
    public static <T> Node<T> findNode(final Node<T> start, final NodeId key) {
        Node<T> current = start;

        while (1 == distance(current.getId(), key).compareTo(distance(current.getNext().getId(), key))) {
            current = current.getNext();
        }

        return current;
    }

    /**
     * Find the responsible node and get the value for the key.
     *
     * @param <T>
     * @param start
     * @param key
     * @return
     */
    public static <T> T lookup(final Node<T> start, final NodeId key) {
        final Node<T> node = findNode(start, key);
        return node.get(key);
    }

    /**
     * Find the responsible node and store the value with the key.
     *
     * @param <T>
     * @param start
     * @param key
     * @param value
     */
    public static <T> void store(final Node<T> start, final NodeId key, final T value) {
        final Node<T> node = findNode(start, key);
        node.put(key, value);
    }
}

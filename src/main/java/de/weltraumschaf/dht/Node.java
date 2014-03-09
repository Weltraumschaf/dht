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

import com.beust.jcommander.internal.Maps;
import de.weltraumschaf.dht.id.NodeId;
import java.util.Map;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Node<T> {

    private final NodeId id;
    private final Node<T> next;
    private final Map<NodeId, T> data = Maps.newHashMap();

    public Node(final NodeId id, final Node<T> next) {
        super();
        this.id = Validate.notNull(id);
        this.next = Validate.notNull(next);
    }

    public NodeId getId() {
        return id;
    }

    public Node<T> getNext() {
        return next;
    }

    public void put(final NodeId key, final T date) {
        data.put(key, date);
    }

    public T get(final NodeId key) {
        Validate.isTrue(has(key), "Does not have key: " + key.toString());
        return data.get(key);
    }

    public boolean has(final NodeId key) {
        return data.containsKey(key);
    }
}

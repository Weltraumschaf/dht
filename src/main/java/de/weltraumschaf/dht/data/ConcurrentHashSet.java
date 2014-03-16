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
package de.weltraumschaf.dht.data;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.Validate;

/**
 * Implement on top of a ConcurrentHashMap with a dummy value.
 *
 * From net.i2p.util
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E> {

    /**
     * Used as default capacity for {@link #data} if none given.
     */
    private static final int DEFAULT_INITIAL_CAPACICTY = 16;
    /**
     * Used as mapped value.
     */
    private static final Object DUMMY_VALUE = new Object();
    /**
     * The data holder for the set.
     *
     * The data itself are the keys. As values {@link #DUMMY_VALUE} will be inserted.
     */
    private final Map<E, Object> data;

    /**
     * Convenience constructor.
     *
     * Initializes size with initial capacity of {@link #DEFAULT_INITIAL_CAPACICTY}.
     */
    public ConcurrentHashSet() {
        this(DEFAULT_INITIAL_CAPACICTY);
    }

    /**
     * Dedicated constructor.
     *
     * @param capacity must not be negative
     */
    public ConcurrentHashSet(final int capacity) {
        super();
        Validate.isTrue(capacity >= 0, "Parameter >capacity< must not be negative!");
        data = new ConcurrentHashMap<E, Object>(capacity);
    }

    @Override
    public boolean add(final E o) {
        return data.put(o, DUMMY_VALUE) == null;
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public boolean contains(final Object o) {
        return data.containsKey(o);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean remove(final Object o) {
        return data.remove(o) != null;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public Iterator<E> iterator() {
        return data.keySet().iterator();
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        boolean changed = false;

        for (final E e : c) {
            changed |= data.put(e, DUMMY_VALUE) == null;
        }

        return changed;
    }

    @Override
    public String toString() {
        return String.format("ConcurrentHashSet{data=%s}", data.keySet());
    }

}

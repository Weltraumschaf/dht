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
package de.weltraumschaf.dht.collection;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implement on top of a ConcurrentHashMap with a dummy value.
 *
 * @author zzz
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E> {

    private static final int INITIAL_DEFAULT_CAPACITY = 16;
    private static final Object DUMMY = new Object();
    private final Map<E, Object> map;

    public ConcurrentHashSet() {
        this(INITIAL_DEFAULT_CAPACITY);
    }

    public ConcurrentHashSet(final int capacity) {
        map = new ConcurrentHashMap<E, Object>(capacity);
    }

    @Override
    public boolean add(final E o) {
        return map.put(o, DUMMY) == null;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(final Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean remove(final Object o) {
        return map.remove(o) != null;
    }

    public int size() {
        return map.size();
    }

    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        boolean rv = false;

        for (E e : c) {
            rv |= map.put(e, DUMMY) == null;
        }

        return rv;
    }
}

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

import java.util.List;
import java.util.Set;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class KBucketSetImpl<T extends KBucketKey> implements KBucketSet {

    @Override
    public boolean add(KBucketKey peer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set getAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set getAll(Set toIgnore) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List getClosest(int max) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List getClosest(KBucketKey key, int max) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(KBucketKey entry) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

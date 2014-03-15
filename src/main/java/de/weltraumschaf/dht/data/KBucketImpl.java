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

import java.util.Collections;
import java.util.Set;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class KBucketImpl<T extends KBucketKey> implements KBucket<T> {

    public KBucketImpl(final int k) {
        super();
    }

    @Override
    public boolean add(final T entry) {
        return false;
    }

    @Override
    public boolean remove(final T entry)  {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<T> getEntries() {
        return Collections.emptySet();
    }

    @Override
    public byte[] data() {
        return new byte[] {};
    }

}

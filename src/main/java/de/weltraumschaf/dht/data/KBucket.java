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

import java.util.Set;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface KBucket<T extends KBucketKey> extends KBucketKey {

    int getRangeBegin();

    int getRangeEnd();

    int getKeyCount();

    /**
     * Sets last-changed if rv is true OR if the peer is already present. Calls the trimmer if begin == end and we are
     * full. If begin != end then add it and caller must do bucket splitting.
     *
     * @return true if added
     */
    boolean add(final T entry);

    /**
     * @return if removed. Does NOT set lastChanged.
     */
    boolean remove(final T entry);

    int size();

    void clear();

    /**
     *  @return an unmodifiable view; not a copy
     */
    Set<T> getEntries();

    /**
     * The last-changed timestamp, which actually indicates last-added or last-seen.
     */
    long getLastChanged();

    /**
     * Update the last-changed timestamp to now.
     */
    void setLastChanged();
}

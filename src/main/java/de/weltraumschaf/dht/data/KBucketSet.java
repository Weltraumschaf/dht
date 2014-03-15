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
public interface KBucketSet<T extends KBucketKey> {

    boolean add(T peer);
    void clear();
    Set<T> getAll();
    Set<T> getAll(Set<T> toIgnore);
    List<T> getClosest(int max);
    List<T> getClosest(T key, int max);
    boolean remove(T entry);
    int size();

}

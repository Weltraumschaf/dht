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

/**
 *  Called when a kbucket can no longer be split and is too big
 *  @since 0.9.2 in i2psnark, moved to core in 0.9.10
 */
public interface KBucketTrimmer<K> {
    /**
     *  Called from add() just before adding the entry.
     *  You may call getEntries() and/or remove() from here.
     *  Do NOT call add().
     *  To always discard a newer entry, always return false.
     *
     *  @param kbucket the kbucket that is now too big
     *  @return true to actually add the entry.
     */
    public boolean trim(KBucket<K> kbucket, K toAdd);
}
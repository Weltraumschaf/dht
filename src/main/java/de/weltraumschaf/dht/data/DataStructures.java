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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class DataStructures {

    private DataStructures() {
        super();
        throw new UnsupportedOperationException("Do not call via refletction!");
    }

    public static <T extends KBucketKey> KBucketSet<T> newBucketSet(final int begin, final int end, final int max, final KBucketTrimmer<T> trimmer) {
        return new KSingleBucketSet<T>(newBucket(begin, end, max, trimmer));
    }

    public static <T extends KBucketKey> KBucket<T> newBucket(final int begin, final int end, final int max, final KBucketTrimmer<T> trimmer) {
        return new KBucketImpl<T>(begin, end, max, trimmer);
    }

}

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
 * Factory to provide data structures.
 *
 * <ul>
 * <li>{@link KBucketSet}</li>
 * <li>{@link KBucket}</li>
 * </ul>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class DataStructures {

    /**
     * Hidden for pure static factory.
     */
    private DataStructures() {
        super();
        throw new UnsupportedOperationException("Do not call via refletction!");
    }

    /**
     * Creates a new k-bucket set.
     *
     * @param <T> type of keys stored in bucket
     * @param begin
     * @param end
     * @param max
     * @param trimmer must not be {@code null}
     * @return never {@code null}, always new instance
     */
    public static <T extends KBucketKey> KBucketSet<T> newBucketSet(final int begin, final int end, final int max, final KBucketTrimmer<T> trimmer) {
        return new KSingleBucketSet<T>(newBucket(begin, end, max, trimmer));
    }

    /**
     * Creates a new k-bucket.
     *
     * @param <T> type of keys
     * @param begin
     * @param end
     * @param max
     * @param trimmer must not be {@code null}
     * @return never {@code null}, always new instance
     */
    public static <T extends KBucketKey> KBucket<T> newBucket(final int begin, final int end, final int max, final KBucketTrimmer<T> trimmer) {
        return new KBucketImpl<T>(begin, end, max, trimmer);
    }

    public static <T extends KBucketKey> KBucketTrimmer<T> newBucketTrimmer() {
        return new KBucketTrimmerImpl<T>();
    }

    private static class KBucketTrimmerImpl<T extends KBucketKey> implements KBucketTrimmer<T> {

        @Override
        public boolean trim(final KBucket<T> kbucket, final T toAdd) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

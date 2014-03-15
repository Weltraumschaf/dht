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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.Validate;

/**
 * Has only one single bucket.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class KSingleBucketSet<T extends KBucketKey> implements KBucketSet<T> {

    /**
     * The single bucket to hold keys.
     */
    private final KBucket<T> bucket;

    /**
     * Dedicated constructor.
     *
     * @param k bucket size
     */
    public KSingleBucketSet(final KBucket<T> bucket) {
        super();
        this.bucket = Validate.notNull(bucket);
    }

    @Override
    public boolean add(final T peer) {
        return bucket.add(peer);
    }

    @Override
    public void clear() {
        bucket.clear();
    }

    @Override
    public Set<T> getAll() {
        final Set<T> all = new HashSet<T>();
        all.addAll(bucket.getEntries());
        return all;
    }

    @Override
    public Set getAll(final Set<T> toIgnore) {
        final Set<T> all = getAll();
        all.removeAll(toIgnore);
        return all;
    }

    @Override
    public List getClosest(final int max) {
        return getClosest(max, Collections.<T>emptySet());
    }

    @Override
    public List<T> getClosest(final int max, final Collection<T> toIgnore) {
        final List<T> closest = new ArrayList<T>(max);

        for (final T entry : bucket.getEntries()) {
            if (!toIgnore.contains(entry)) {
                closest.add(entry);
            }
        }

        final Comparator<KBucketKey> comp = new XorComparator<>(bucket);
        Collections.sort(closest, comp);

        for (int i = closest.size() - 1; i >= max; i--) {
            closest.remove(i);
        }

        return closest;
    }

    @Override
    public List getClosest(final T key, int max) {
        return getClosest(key, max, Collections.<T>emptySet());
    }

    @Override
    public List<T> getClosest(final T key, final int max, final Collection<T> toIgnore) {
        if (KBucketKey.IsEqual.isEqual(key, bucket)) {
            return getClosest(max, toIgnore);
        }

        return getClosest(max, Collections.<T>emptySet());
    }

    @Override
    public boolean remove(final T entry) {
        return bucket.remove(entry);
    }

    @Override
    public int size() {
        return bucket.size();
    }

}

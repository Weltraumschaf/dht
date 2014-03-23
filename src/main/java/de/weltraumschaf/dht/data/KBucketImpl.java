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
import org.apache.commons.lang3.Validate;

/**
 * A concurrent implementation using ConcurrentHashSet. The max size (K) may be temporarily exceeded due to concurrency,
 * a pending split, or the behavior of the supplied trimmer, as explained below. The creator is responsible for splits.
 *
 * This class has no knowledge of the DHT base used for XORing, and thus there are no validity checks in add/remove.
 *
 * The begin and end values are immutable. All entries in this bucket will have at least one bit different from us in
 * the range [begin, end] inclusive. Splits must be implemented by creating two new buckets and discarding this one.
 *
 * The keys are kept in a Set and are NOT sorted by last-seen. Per-key last-seen-time, failures, etc. must be tracked
 * elsewhere.
 *
 * If this bucket is full (i.e. begin == end && size == max) then add() will call KBucketTrimmer.trim() do (possibly)
 * remove older entries, and indicate whether to add the new entry. If the trimmer returns true without removing
 * entries, this KBucket will exceed the max size.
 *
 * Refactored from net.i2p.router.networkdb.kademlia
 *
 * @since 0.9.2 in i2psnark, moved to core in 0.9.10
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class KBucketImpl<T extends KBucketKey> implements KBucket<T> {

    /**
     * set of Hash objects for the peers in the kbucket
     */
    private final Set<T> entries;
    /**
     * include if any bits equal or higher to this bit (in big endian order)
     */
    private final int begin;
    /**
     * include if no bits higher than this bit (inclusive) are set
     */
    private final int end;
    private final int max;
    private final KBucketTrimmer<T> trimmer;
    /**
     * when did we last shake things up
     */
    private long _lastChanged;

    /**
     * All entries in this bucket will have at least one bit different from us in the range [begin, end] inclusive.
     */
    public KBucketImpl(final int begin, final int end, final int max, final KBucketTrimmer<T> trimmer) {
        super();
        Validate.isTrue(begin <= end, begin + " > " + end);
        this.entries = new ConcurrentHashSet<T>(max + 4);
        this.begin = begin;
        this.end = end;
        this.max = max;
        this.trimmer = Validate.notNull(trimmer, "Parameter >trimmer< must not be null!");
    }

    @Override
    public int getRangeBegin() {
        return begin;
    }

    @Override
    public int getRangeEnd() {
        return end;
    }

    @Override
    public int getKeyCount() {
        return entries.size();
    }

    @Override
    public boolean add(final T entry) {
        if (begin != end || entries.size() < max
                || entries.contains(entry) || trimmer.trim(this, entry)) {
            // do this even if already contains, to call setLastChanged()
            boolean rv = entries.add(entry);
            setLastChanged();
            return rv;
        }
        return false;
    }

    @Override
    public boolean remove(final T entry) {
        boolean rv = entries.remove(entry);
        //if (rv)
        //    setLastChanged();
        return rv;
    }

    /**
     * Update the last-changed timestamp to now.
     */
    public void setLastChanged() {
//        _lastChanged = _context.clock().now();
    }

    /**
     * The last-changed timestamp, which actually indicates last-added or last-seen.
     */
    public long getLastChanged() {
        return _lastChanged;
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public Set<T> getEntries() {
        return Collections.unmodifiableSet(entries);
    }

    @Override
    public byte[] data() {
        return new byte[]{};
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(1024);
        buf.append(entries.size());
        buf.append(" entries in (").append(begin).append(',').append(end);
        buf.append(") : ").append(entries.toString());
        return buf.toString();
    }

}

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

import java.util.Collections;
import java.util.Set;

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
 */
public class KBucket<T> {

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
    private long lastChanged;

    /**
     * All entries in this bucket will have at least one bit different from us in the range [begin, end] inclusive.
     */
    public KBucket(int begin, int end, int max, KBucketTrimmer<T> trimmer) {
        super();

        if (begin > end) {
            throw new IllegalArgumentException(begin + " > " + end);
        }

        this.entries = new ConcurrentHashSet<T>(max + 4);
        this.begin = begin;
        this.end = end;
        this.max = max;
        this.trimmer = trimmer;
    }

    public int getRangeBegin() {
        return begin;
    }

    public int getRangeEnd() {
        return end;
    }

    public int getKeyCount() {
        return entries.size();
    }

    /**
     * @return an unmodifiable view; not a copy
     */
    public Set<T> getEntries() {
        return Collections.unmodifiableSet(entries);
    }

    public void getEntries(final SelectionCollector<T> collector) {
        for (T h : entries) {
            collector.add(h);
        }
    }

    public void clear() {
        entries.clear();
    }

    /**
     * Sets last-changed if rv is true OR if the peer is already present. Calls the trimmer if begin == end and we are
     * full. If begin != end then add it and caller must do bucket splitting.
     *
     * @return true if added
     */
    public boolean add(final T peer) {
        if (begin != end || entries.size() < max || entries.contains(peer) || trimmer.trim(this, peer)) {
            // do this even if already contains, to call setLastChanged()
            boolean rv = entries.add(peer);
//            setLastChanged();
            return rv;
        }
        return false;
    }

    /**
     * @return if removed. Does NOT set lastChanged.
     */
    public boolean remove(final T peer) {
        boolean rv = entries.remove(peer);
        //if (rv)
        //    setLastChanged();
        return rv;
    }

    /**
     * Update the last-changed timestamp to now.
     */
    public void setLastChanged(final long now) {
        lastChanged = now;
    }

    /**
     * The last-changed timestamp, which actually indicates last-added or last-seen.
     */
    public long getLastChanged() {
        return lastChanged;
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

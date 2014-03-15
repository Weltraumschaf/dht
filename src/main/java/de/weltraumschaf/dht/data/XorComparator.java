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

import java.util.Comparator;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class XorComparator<T extends KBucketKey> implements Comparator<T> {

    public static final int EQUAL = 0;
    public static final int GREATER_THAN = 1;
    public static final int LESS_THAN = -1;

    private final byte[] base;

    /**
     * @param target key to compare distances with
     */
    public XorComparator(final KBucketKey target) {
        super();
        base = Validate.notNull(target).data();
    }

    @Override
    public int compare(final T lhs, final T rhs) {
        byte leftBytes[] = Validate.notNull(lhs).data();
        byte rightBytes[] = Validate.notNull(rhs).data();

        for (int i = 0; i < base.length; i++) {
            final int leftInt = (leftBytes[i] ^ base[i]) & 0xff;
            final int rightInt = (rightBytes[i] ^ base[i]) & 0xff;

            if (leftInt < rightInt) {
                return LESS_THAN;
            }

            if (leftInt > rightInt) {
                return GREATER_THAN;
            }
        }

        return EQUAL;
    }
}

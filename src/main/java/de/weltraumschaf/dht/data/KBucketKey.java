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

import java.util.Arrays;

/**
 * Implementors can be used as key.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface KBucketKey {

    /**
     * Returns binary key representation of the implementing type.
     *
     * @return never {@code null}, never empty empty
     */
    byte[] data();

    public static final class IsEqual {

        public static boolean isEqual(final KBucketKey lhs, final KBucketKey rhs) {
            if (lhs == null && rhs == null) {
                return false;
            }

            if (lhs == rhs) {
                return true;
            }

            return Arrays.equals(lhs.data(), rhs.data());
        }
    }
}

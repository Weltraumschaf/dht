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

}

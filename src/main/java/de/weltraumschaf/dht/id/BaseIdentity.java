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

package de.weltraumschaf.dht.id;

import java.util.Arrays;
import org.apache.commons.codec.binary.Hex;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseIdentity implements Identity {

    private final byte[] id;

    public BaseIdentity(byte[] id) {
        super();
        this.id = id;
    }

    @Override
    public byte[] getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Identity)) {
            return false;
        }

        final Identity other = (Identity) obj;
        return Arrays.equals(id, other.getId());
    }


    @Override
    public String toString() {
        return Hex.encodeHexString(id);
    }

}

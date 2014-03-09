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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;

/**
 * Provides utility function for UUIDs.
 *
 * From http://dev-svn.seasr.org/WebSVN/filedetails.php?repname=Components&path=%2FCommons%2FSEASR-Commons%2Ftrunk%2Fsrc%2Forg%2Fseasr%2Fmeandre%2Fsupport%2Fgeneric%2Futil%2FUUIDUtils.java&rev=1844&sc=1
 *
 * @author Boris Capitanu
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class UuidConverter {

    private UuidConverter() {
        super();
    }

    /**
     * Converts a UUID to a byte array
     *
     * @param uuid The UUID
     * @return The byte array representing the UUID provided, or null if it can't be computed
     */
    public static byte[] toByteArray(UUID uuid) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(baos);

        try {
            outputStream.writeLong(uuid.getMostSignificantBits());
            outputStream.writeLong(uuid.getLeastSignificantBits());
        }
        catch (IOException e) {
            // Should not happen
            return null;
        }

        return baos.toByteArray();
    }

    /**
     * Reconstructs a UUID from a byte array
     *
     * @param bytes The byte array (will be automatically padded left if size < 16)
     * @return The UUID
     */
    public static UUID fromByteArray(byte[] bytes) {
        int usableBytes = Math.min(bytes.length, 16);

        // Need exactly 16 bytes - pad the input if not enough bytes are provided
        // Use provided bytes in the least significant position; if more than 16 bytes are given,
        // then use the first 16 bytes from the array;
        byte[] barr = new byte[16];
        for (int i = 15, j = usableBytes-1; j >= 0; i--, j--)
            barr[i] = bytes[j];

        ByteArrayInputStream bais = new ByteArrayInputStream(barr);
        DataInputStream inputStream = new DataInputStream(bais);

        try {
            long msb = inputStream.readLong();
            long lsb = inputStream.readLong();

            return new UUID(msb, lsb);
        }
        catch (IOException e) {
            // Should never happen
            return null;
        }
    }

    /**
     * Converts a UUID to a BigInteger
     *
     * @param uuid The UUID
     * @return The BigInteger
     */
    public static BigInteger toBigInteger(UUID uuid) {
        return new BigInteger(toByteArray(uuid));
    }

    /**
     * Reconstructs a UUID from a BigInteger
     *
     * @param bigInteger The big integer
     * @return The UUID
     */
    public static UUID fromBigInteger(BigInteger bigInteger) {
        return fromByteArray(bigInteger.toByteArray());
    }
}

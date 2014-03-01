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
package de.weltraumschaf.dht;

import java.util.Arrays;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Bits {

    static final byte EMPTY = (byte) 0x00;

    public static String formatByteToBitString(final byte b) {
        return String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
    }

    public static byte[] pad(final byte[] in, final int size) {
        Validate.notNull(in, "Parameter >in< must not be null!");
        Validate.isTrue(size > 0, "Parameter >size< must be greater than 0!");

        if (in.length >= size) {
            return in;
        }

        final byte[] out = new byte[size];
        final int offset = size - in.length;

        for (int i = 0; i < offset; ++i) {
            out[i] = (byte) 0x00;
        }

        System.arraycopy(in, 0, out, offset, in.length);

        return out;
    }

    public static byte[] xor(final byte[] inputA, final byte[] inputB) {
        Validate.notNull(inputA, "Parameter >inputA< must not be null!");
        Validate.notNull(inputB, "Parameter >inputB< must not be null!");

        final int maxLength = Math.max(inputA.length, inputB.length);
        final byte[] result = new byte[maxLength];
        final byte[] paddedInputA = pad(inputA, maxLength);
        final byte[] paddedInputB = pad(inputB, maxLength);

        for (int i = 0; i < maxLength; ++i) {
            final byte byteA = paddedInputA[i];
            final byte byteB = paddedInputB[i];
            result[i] = (byte) (byteA ^ byteB);
        }

        return result;
    }

    public static CompareResult compare(final byte[] inputA, final byte[] inputB) {
        Validate.notNull(inputA, "Parameter >inputA< must not be null!");
        Validate.notNull(inputB, "Parameter >inputB< must not be null!");

        final int maxLength = Math.max(inputA.length, inputB.length);
        Validate.isTrue(maxLength > 0, "At least one given array must not be empty!");
        final byte[] paddedInputA = pad(inputA, maxLength);
        final byte[] paddedInputB = pad(inputB, maxLength);

        for (int i = 0; i < maxLength; ++i) {
            final byte byteA = paddedInputA[i];
            final byte byteB = paddedInputB[i];

            if (byteA < byteB) {
                return CompareResult.LESS;
            }

            if (byteA > byteB) {
                return CompareResult.GREATER;
            }
        }

        return CompareResult.EQUAL;
    }

    public static enum CompareResult {
        LESS, EQUAL, GREATER;
    }
}

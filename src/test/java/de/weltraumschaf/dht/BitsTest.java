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

import de.weltraumschaf.dht.Bits.CompareResult;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {2link Bits}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BitsTest {

    @Test
    public void formatByteToBitString() {
        assertThat(Bits.formatByteToBitString((byte) 0x00), is("00000000"));
        assertThat(Bits.formatByteToBitString((byte) 0x0f), is("00001111"));
        assertThat(Bits.formatByteToBitString((byte) 0x23), is("00100011"));
        assertThat(Bits.formatByteToBitString((byte) 0x42), is("01000010"));
        assertThat(Bits.formatByteToBitString((byte) 0xa2), is("10100010"));
        assertThat(Bits.formatByteToBitString((byte) 0xc5), is("11000101"));
        assertThat(Bits.formatByteToBitString((byte) 0xf0), is("11110000"));
        assertThat(Bits.formatByteToBitString((byte) 0xf3), is("11110011"));
        assertThat(Bits.formatByteToBitString((byte) 0xfa), is("11111010"));
        assertThat(Bits.formatByteToBitString((byte) 0xff), is("11111111"));
    }

    @Test
    public void pad() {
        assertThat(Bits.pad(new byte[]{}, 1), is(new byte[]{Bits.EMPTY}));

        assertThat(Bits.pad(new byte[]{}, 10), is(new byte[]{
            Bits.EMPTY, Bits.EMPTY, Bits.EMPTY, Bits.EMPTY, Bits.EMPTY,
            Bits.EMPTY, Bits.EMPTY, Bits.EMPTY, Bits.EMPTY, Bits.EMPTY
        }));

        assertThat(Bits.pad(new byte[]{(byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05}, 10), is(new byte[]{
            Bits.EMPTY, Bits.EMPTY, Bits.EMPTY, Bits.EMPTY, Bits.EMPTY,
            (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05
        }));
    }

    @Test
    public void xor() {
        assertThat(Bits.xor(new byte[]{(byte) 0xf0}, new byte[]{(byte) 0x0f}), is(new byte[]{(byte) 0xff}));
        assertThat(Bits.xor(new byte[]{(byte) 0xff}, new byte[]{(byte) 0x0f}), is(new byte[]{(byte) 0xf0}));
        assertThat(Bits.xor(new byte[]{(byte) 0xff}, new byte[]{(byte) 0xf0}), is(new byte[]{(byte) 0x0f}));
        assertThat(Bits.xor(new byte[]{(byte) 0xff}, new byte[]{(byte) 0x00}), is(new byte[]{(byte) 0xff}));

        assertThat(Bits.xor(new byte[]{(byte) 0xf0, (byte) 0xff}, new byte[]{(byte) 0x0f, (byte) 0x0f}),
                is(new byte[]{(byte) 0xff, (byte) 0xf0}));

        assertThat(Bits.xor(new byte[]{(byte) 0xf0, (byte) 0xff}, new byte[]{(byte) 0x0f}),
                is(new byte[]{(byte) 0xf0, (byte) 0xf0}));

        assertThat(Bits.xor(new byte[]{(byte) 0xff}, new byte[]{(byte) 0x0f, (byte) 0x0f}),
                is(new byte[]{(byte) 0x0f, (byte) 0xf0}));
    }

    @Test
    public void compare() {
        assertThat(Bits.compare(new byte[] {Bits.EMPTY}, new byte[] {}), is(CompareResult.EQUAL));
        assertThat(Bits.compare(new byte[] {}, new byte[] {Bits.EMPTY}), is(CompareResult.EQUAL));

        assertThat(Bits.compare(new byte[] {Bits.EMPTY}, new byte[] {Bits.EMPTY}),
                is(CompareResult.EQUAL));
        assertThat(Bits.compare(new byte[] {Bits.EMPTY, Bits.EMPTY}, new byte[] {Bits.EMPTY}),
                is(CompareResult.EQUAL));
        assertThat(Bits.compare(new byte[] {Bits.EMPTY}, new byte[] {Bits.EMPTY, Bits.EMPTY}),
                is(CompareResult.EQUAL));
        assertThat(Bits.compare(new byte[] {Bits.EMPTY, Bits.EMPTY}, new byte[] {Bits.EMPTY, Bits.EMPTY}),
                is(CompareResult.EQUAL));

        assertThat(Bits.compare(new byte[] {0x01}, new byte[] {0x02}),
                is(CompareResult.LESS));

        assertThat(Bits.compare(new byte[] {0x01, 0x01}, new byte[] {0x01, 0x02}),
                is(CompareResult.LESS));

        assertThat(Bits.compare(new byte[] {0x03}, new byte[] {0x02}),
                is(CompareResult.GREATER));

        assertThat(Bits.compare(new byte[] {0x02, 0x03}, new byte[] {0x02, 0x02}),
                is(CompareResult.GREATER));
    }
}

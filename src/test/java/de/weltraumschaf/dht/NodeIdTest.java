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

import java.math.BigInteger;
import java.util.UUID;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for {@link NodeId}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NodeIdTest {

    @Test
    public void asInteger() {

//        final long high = 4121525995650369167L; // 0x 39 32 9A 0E 67 47 46 8F
//        final long low = -5808030885311500692L;
//        final NodeId id = new NodeId(new UUID(high, low));
//        assertThat(id.asInteger().toString(), is(equalTo("")));
//        BigInteger i = BigInteger.valueOf(0xa1L);
//        System.out.println(i.toString(16));
//        i = i.shiftLeft(8);
//        System.out.println(i.toString(16));
//        i = i.add(BigInteger.valueOf(0xb2L));
//        System.out.println(i.toString(16));
    }

    @Test public void create128bitInteger() {
        final long high = 0xa1a1a1a1a1a1a1a1L;
        final long low = 0xb2b2b2b2b2b2b2b2L;

        assertThat(NodeId.create128bitInteger(high, low).toString(16)
                , is(equalTo("a1a1a1a1a1a1a1a10000000000000000")));
    }

}

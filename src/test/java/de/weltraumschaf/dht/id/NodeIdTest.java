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

import de.weltraumschaf.dht.id.NodeId;
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

    private static final String UUID_AS_STRING = "f07bb8b7-cdf9-4731-8992-d538dc3abc0b";
    private static final String UUID_AS_BIGINT = "-f8447483206b8ce766d2ac723c543f5";
    private final NodeId sut = new NodeId(UUID.fromString(UUID_AS_STRING));

    @Test
    public void asInteger() {
        assertThat(sut.asInteger(), is(equalTo(new BigInteger(UUID_AS_BIGINT, 16))));
    }

    @Test
    public void asString() {
        assertThat(sut.asString(), is(equalTo(UUID_AS_STRING)));
    }

}

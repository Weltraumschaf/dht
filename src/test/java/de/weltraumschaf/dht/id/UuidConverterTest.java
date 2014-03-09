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

import java.io.IOException;
import java.math.BigInteger;
import java.util.UUID;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link UuidConverter}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class UuidConverterTest {

    private static final String UUID_AS_STRING = "f07bb8b7-cdf9-4731-8992-d538dc3abc0b";
    private static final String UUID_AS_BIGINT = "-f8447483206b8ce766d2ac723c543f5";
    private static final byte[] UUID_AS_BYTES = new byte[]{
        -16, 123, -72, -73, -51, -7, 71, 49, -119, -110, -43, 56, -36, 58, -68, 11
    };
    private final UUID uuid = UUID.fromString(UUID_AS_STRING);

    @Test
    public void toByteArray() throws IOException {
        assertThat(UuidConverter.toByteArray(uuid),
                is(UUID_AS_BYTES));
    }

    @Test
    public void fromByteArray() throws IOException {
        assertThat(UuidConverter.fromByteArray(UUID_AS_BYTES), is(equalTo(uuid)));
    }

    @Test
    public void toBigInteger() throws IOException {
        assertThat(UuidConverter.toBigInteger(uuid).toString(16),
                is(UUID_AS_BIGINT));
    }

    @Test
    public void fromBigInteger() throws IOException {
        assertThat(UuidConverter.fromBigInteger(new BigInteger(UUID_AS_BIGINT, 16)).toString(),
                is(UUID_AS_STRING));
    }

}

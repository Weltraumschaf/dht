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
package de.weltraumschaf.dht.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import de.weltraumschaf.dht.msg.MessageSerializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link NetworkAddress}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NetworkAddressTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final Kryo kryo = MessageSerializer.newSerializer();

    @Test
    public void constructWithNullHostnThrowsExcpetion() {
        thrown.expect(NullPointerException.class);
        new NetworkAddress(null, 0);
    }

    @Test
    public void constructWithEmptyHostnThrowsExcpetion() {
        thrown.expect(IllegalArgumentException.class);
        new NetworkAddress("", 0);
    }

    @Test
    public void constructWithNegativePort() {
        thrown.expect(IllegalArgumentException.class);
        new NetworkAddress("host", -1);
    }

    @Test
    public void serialization() throws IOException {
        final NetworkAddress in = new NetworkAddress("foo", 1234);
        final byte[] data;

        try (final Output output = new Output(new ByteArrayOutputStream())) {
            kryo.writeObject(output, in);
            output.flush();
            data = output.getBuffer();
        }

        final Input input = new Input(new ByteArrayInputStream(data));
        final NetworkAddress out = kryo.readObject(input, NetworkAddress.class);
        input.close();

        assertThat(out, is(equalTo(in)));
    }

    @Test
    public void toString_withoutPort() {
        assertThat(new NetworkAddress("foobar", 0).toString(), is(equalTo("foobar")));
    }

    @Test
    public void toString_withPort() {
        assertThat(new NetworkAddress("foobar", 6666).toString(), is(equalTo("foobar:6666")));
    }

    @Test
    public void forValue_hostOnly() {
        assertThat(NetworkAddress.forValue("hostname"), is(equalTo(new NetworkAddress("hostname", 0))));
    }

    @Test
    public void forValue_hostAndPort() {
        assertThat(NetworkAddress.forValue("hostname:8888"), is(equalTo(new NetworkAddress("hostname", 8888))));
    }
}

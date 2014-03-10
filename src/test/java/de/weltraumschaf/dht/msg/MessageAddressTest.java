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

package de.weltraumschaf.dht.msg;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import static de.weltraumschaf.dht.msg.MessageSerializer.newSerializer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link MessageAddress}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MessageAddressTest {

    private final Kryo kryo = newSerializer();

    @Test
    public void serialization() throws IOException {
        final MessageAddress in = new MessageAddress("foo", 1234);

        final Output output = new Output(new ByteArrayOutputStream());
        kryo.writeObject(output, in);
        output.flush();
        final byte[] data = output.getBuffer();
        output.close();

        final Input input = new Input(new ByteArrayInputStream(data));
        final MessageAddress out = kryo.readObject(input, MessageAddress.class);
        input.close();

        assertThat(out, is(equalTo(in)));
    }

}

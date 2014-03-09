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

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.msgpack.MessagePack;

/**
 * Tests for {@link MessageAddress}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MessageAddressTest {

    @Test
    public void serialization() throws IOException {
        final MessageAddress in = new MessageAddress("foo", 1234);
        final MessagePack msgpack = new MessagePack();
        final byte[] data = msgpack.write(in);

        assertThat(msgpack.read(data, MessageAddress.class), is(equalTo(in)));
    }

}

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
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.msgpack.MessagePack;

/**
 * Tests for {@link MessageSerianlizer}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MessageSerianlizerTest {

    private final MessageSerianlizer sut = new MessageSerianlizer();

    @Test
    public void serialize() throws IOException {
        final Message in = new TextMessage(new MessageAddress("from", 1), new MessageAddress("to", 2), "data");

        assertThat(
            sut.serialize(in),
            is(equalTo(new RawMessage(in.getType().value(), new MessagePack().write(in)))));
    }

    @Test @Ignore
    public void deserialize() throws IOException {
        final Message expected = new TextMessage(new MessageAddress("from", 1), new MessageAddress("to", 2), "data");

        assertThat(
            sut.deserialize(sut.serialize(expected)),
            is(equalTo(expected)));
    }

}

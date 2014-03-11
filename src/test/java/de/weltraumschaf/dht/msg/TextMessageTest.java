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

import de.weltraumschaf.dht.net.NetworkAddress;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link TextMessage}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TextMessageTest {

    private final NetworkAddress from = new NetworkAddress("127.0.0.1", 4444);
    private final NetworkAddress to = new NetworkAddress("127.0.0.1", 5555);
    private final TextMessage sut = new TextMessage(from, to, "text");

    @Test
    public void getFrom() {
        assertThat(sut.getFrom(), is(sameInstance(from)));
    }

    @Test
    public void getBody() {
        assertThat(sut.getBody(), is(equalTo("text")));
    }

    @Test
    public void markAsRead() {
        assertThat(sut.isUnread(), is(true));
        sut.markAsRead();
        assertThat(sut.isUnread(), is(false));
        sut.markAsRead();
        assertThat(sut.isUnread(), is(false));
    }

}

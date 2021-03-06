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
 * Tests for }{@link DefaultMessageBox}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DefaultMessageBoxTest {

    private final NetworkAddress from = new NetworkAddress("127.0.0.1", 4444);
    private final NetworkAddress to = new NetworkAddress("127.0.0.1", 5555);
    private final MessageBox sut = new DefaultMessageBox();

    @Test
    public void putAndRemoveMessages() {
        final Message m1 = Messaging.newTextMessage(from, to, "foo");
        final Message m2 = Messaging.newTextMessage(from, to,  "bar");
        final Message m3 = Messaging.newTextMessage(from, to,  "baz");

        assertThat(sut.count(), is(0));
        assertThat(sut.countUnread(), is(0));

        sut.put(m1);
        assertThat(sut.count(), is(1));
        assertThat(sut.countUnread(), is(1));

        sut.put(m2);
        assertThat(sut.count(), is(2));
        assertThat(sut.countUnread(), is(2));

        sut.put(m3);
        assertThat(sut.count(), is(3));
        assertThat(sut.countUnread(), is(3));

        sut.remove(2);
        assertThat(sut.count(), is(2));
        assertThat(sut.countUnread(), is(2));

        sut.remove(1);
        assertThat(sut.count(), is(1));
        assertThat(sut.countUnread(), is(1));

        sut.remove(0);
        assertThat(sut.count(), is(0));
        assertThat(sut.countUnread(), is(0));
    }

    @Test
    public void countUnread() {
        final Message m1 = Messaging.newTextMessage(from, to,  "foo");
        final Message m2 = Messaging.newTextMessage(from, to,  "bar");
        final Message m3 = Messaging.newTextMessage(from, to,  "baz");

        sut.put(m1);
        sut.put(m2);
        sut.put(m3);

        assertThat(sut.count(), is(3));
        assertThat(sut.countUnread(), is(3));

        m2.markAsRead();
        assertThat(sut.count(), is(3));
        assertThat(sut.countUnread(), is(2));

        m3.markAsRead();
        assertThat(sut.count(), is(3));
        assertThat(sut.countUnread(), is(1));

        m1.markAsRead();
        assertThat(sut.count(), is(3));
        assertThat(sut.countUnread(), is(0));
    }

}

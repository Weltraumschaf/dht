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

package de.weltraumschaf.dht.server;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Server}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ServerTest {

    @Test
    public void calculateTimeout() {
        assertThat(Server.calculateTimeout(0), is(1000));
        assertThat(Server.calculateTimeout(1), is(2000));
        assertThat(Server.calculateTimeout(2), is(4000));
        assertThat(Server.calculateTimeout(3), is(8000));
        assertThat(Server.calculateTimeout(4), is(16000));
        assertThat(Server.calculateTimeout(5), is(32000));
        assertThat(Server.calculateTimeout(6), is(64000));
    }

}

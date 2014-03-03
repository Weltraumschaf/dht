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
 * Tests for {2link PortValidator}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PortValidatorTest {

    @Test
    public void isValid() {
        assertThat(PortValidator.isValid(-23), is(false));
        assertThat(PortValidator.isValid(-1), is(false));
        assertThat(PortValidator.isValid(0), is(false));
        assertThat(PortValidator.isValid(1), is(false));
        assertThat(PortValidator.isValid(5), is(false));
        assertThat(PortValidator.isValid(1000), is(false));
        assertThat(PortValidator.isValid(1024), is(false));

        assertThat(PortValidator.isValid(1025), is(true));
        assertThat(PortValidator.isValid(1026), is(true));
        assertThat(PortValidator.isValid(4096), is(true));
        assertThat(PortValidator.isValid(8888), is(true));
        assertThat(PortValidator.isValid(60000), is(true));
        assertThat(PortValidator.isValid(65535), is(true));

        assertThat(PortValidator.isValid(65536), is(false));
        assertThat(PortValidator.isValid(65537), is(false));
        assertThat(PortValidator.isValid(66666), is(false));
        assertThat(PortValidator.isValid(100000000), is(false));
    }

    @Test
    public void range() {
        assertThat(PortValidator.range(), is("1025..65535"));
    }

}

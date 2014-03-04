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
package de.weltraumschaf.dht.event;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link Event}.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public class EventTest {

    @Test
    public void testToString() {
        final Observable source = mock(Observable.class);
        final Event sut = new Event(Event.Type.DEFAULT, "foo", source);
        assertThat(sut.toString(), is(String.format("[DEFAULT] foo (from %s)", source.toString())));
    }

}

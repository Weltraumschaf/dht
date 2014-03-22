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
package de.weltraumschaf.dht.log;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link TacoliMessage}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TacoliMessageTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final TacoliMessage sut = new TacoliMessage();

    @Test
    public void message_throwsExceptionIfMessageIsNull() {
        thrown.expect(NullPointerException.class);
        sut.message(null);
    }

    @Test
    public void message_throwsExceptionIfMessageIsEmpty() {
        thrown.expect(IllegalArgumentException.class);
        sut.message("");
    }

    @Test
    public void add_throwsExceptionIfKeyIsNull() {
        thrown.expect(NullPointerException.class);
        sut.add(null, "foo");
    }

    @Test
    public void add_throwsExceptionIfKeyIsEmpty() {
        thrown.expect(IllegalArgumentException.class);
        sut.add("", "foo");
    }

    @Test
    public void add_throwsExceptionIfValueIsNull() {
        thrown.expect(NullPointerException.class);
        sut.add("foo", null);
    }

    @Test
    public void toString_noEntries() {
        assertThat(sut.toString(), is(equalTo("")));
    }

    @Test
    public void toString_oneEntries() {
        sut.add("foo", "foo_value");
        assertThat(sut.toString(), is(equalTo("foo=foo_value")));
    }

    @Test
    public void toString_twoEntries() {
        sut.add("foo", "foo_value");
        sut.add("bar", "bar_value");
        assertThat(sut.toString(), is(equalTo("foo=foo_value\tbar=bar_value")));
    }

    @Test
    public void toString_threeEntries() {
        sut.add("foo", "foo_value");
        sut.add("bar", "bar_value");
        sut.add("baz", "baz_value");
        assertThat(sut.toString(), is(equalTo("foo=foo_value\tbar=bar_value\tbaz=baz_value")));
    }

    @Test
    public void toString_messageAdded() {
        sut.message("foobar");
        assertThat(sut.toString(), is(equalTo("message=foobar")));
    }

}

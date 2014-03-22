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

import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link TacoliLayout}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TacoliLayoutTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final TacoliLayout sut = new TacoliLayout();

    private LoggingEvent createEvent(final Object message) {
        return new LoggingEvent(
            "class", mock(Category.class), 12345678l, Level.FATAL, message, new Throwable());
    }
    @Test
    public void format_throwsExceptionIfNullPassedIn() {
        thrown.expect(NullPointerException.class);
        sut.format(null);
    }

    @Test
    public void format_simpleStringMessage() {
        assertThat(sut.format(createEvent("message")), is(equalTo("1970-01-01T04:25+0100\tFATAL\tmessage\n")));
    }

    @Test
    public void format_withEmptyTacoliMessage() {
        final TacoliMessage message = new TacoliMessage();
        assertThat(sut.format(createEvent(message)), is(equalTo("1970-01-01T04:25+0100\tFATAL\t\n")));
    }

    @Test
    public void format_withSimpleMessageTacoliMessage() {
        final TacoliMessage message = new TacoliMessage();
        message.message("foobar");
        assertThat(sut.format(createEvent(message)), is(equalTo("1970-01-01T04:25+0100\tFATAL\tmessage=foobar\n")));
    }

    @Test
    public void format_withTacoliMessage() {
        final TacoliMessage message = new TacoliMessage();
        message.add("foo", "bar");
        message.add("baz", "snafu");
        assertThat(sut.format(createEvent(message)), is(equalTo("1970-01-01T04:25+0100\tFATAL\tfoo=bar\tbaz=snafu\n")));
    }

}

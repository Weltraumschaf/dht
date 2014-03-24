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

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;

/**
 * Tests for {@link PidPrefixDailyRollingFileAppender}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class PidPrefixDailyRollingFileAppenderTest {

    @Test
    @Ignore("Not ready yet.")
    public void setName() {
    }

    @Test
    public void findPid() {
        assertThat(PidPrefixDailyRollingFileAppender.findPid(""), is(-1));
        assertThat(PidPrefixDailyRollingFileAppender.findPid("foo"), is(-1));
        assertThat(PidPrefixDailyRollingFileAppender.findPid("foo@bar"), is(-1));
        assertThat(PidPrefixDailyRollingFileAppender.findPid("@bar"), is(-1));
        assertThat(PidPrefixDailyRollingFileAppender.findPid("foo@"), is(-1));
        assertThat(PidPrefixDailyRollingFileAppender.findPid("1234@bar"), is(1234));
        assertThat(PidPrefixDailyRollingFileAppender.findPid("1234"), is(-1));

        assertThat(PidPrefixDailyRollingFileAppender.findPid("1234@"), is(1234));
        assertThat(PidPrefixDailyRollingFileAppender.findPid("42@foobar"), is(42));
    }
}

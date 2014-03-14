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
package de.weltraumschaf.dht;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Clock}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ClockTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_throwsExceptionIfSourceIsNull() {
        thrown.expect(NullPointerException.class);
        new Clock(null);
    }

    @Test
    public void now_throwsExceptionIfNotStarted() {
        thrown.expect(IllegalStateException.class);
        new Clock().now();
    }

    @Test
    public void elapsed_throwsExceptionIfNotStarted() {
        thrown.expect(IllegalStateException.class);
        new Clock().elapsed();
    }

    @Test
    public void now() {
        final Clock sut = new Clock(new TestTimeSource()).start();

        assertThat(sut.now(), is(11l));
        assertThat(sut.now(), is(12l));
        assertThat(sut.now(), is(13l));
        assertThat(sut.now(), is(14l));
    }

    @Test
    public void elapsed() {
        final Clock sut = new Clock(new TestTimeSource()).start();

        assertThat(sut.elapsed(), is(1l));
        assertThat(sut.elapsed(), is(2l));
        assertThat(sut.elapsed(), is(3l));
        assertThat(sut.elapsed(), is(4l));
    }

    private static final class TestTimeSource implements Clock.TimeSource {

        private long time = 10;

        @Override
        public long nanoTime() {
            return time++;
        }

    }
}

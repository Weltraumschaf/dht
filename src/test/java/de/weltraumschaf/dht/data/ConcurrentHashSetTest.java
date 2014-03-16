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
package de.weltraumschaf.dht.data;

import java.util.Arrays;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;

/**
 * Tests for {@link ConcurrentHashSet}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConcurrentHashSetTest {

    private final Set<String> sut = new ConcurrentHashSet<String>();

    @Test
    public void add() {
        assertThat(sut.isEmpty(), is(true));

        sut.add("foo");
        assertThat(sut.size(), is(1));

        sut.add("bar");
        assertThat(sut.size(), is(2));

        sut.add("baz");
        assertThat(sut.size(), is(3));

        sut.add("foo");
        assertThat(sut.size(), is(3));
    }

    @Test
    public void addAll() {
        assertThat(sut.isEmpty(), is(true));

        sut.addAll(Arrays.asList("foo", "bar", "baz", "foo"));
        assertThat(sut.size(), is(3));
    }

    @Test
    @Ignore
    public void clear() {
    }

    @Test
    @Ignore
    public void contains() {
    }

    @Test
    @Ignore
    public void isEmpty() {
    }

    @Test
    @Ignore
    public void size() {
    }

    @Test
    @Ignore
    public void remove() {
    }

    @Test
    @Ignore
    public void iterator() {
    }

    @Test
    @Ignore
    public void testToString() {
    }
}

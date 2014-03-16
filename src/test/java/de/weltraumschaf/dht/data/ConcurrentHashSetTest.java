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
import java.util.Iterator;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link ConcurrentHashSet}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConcurrentHashSetTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private final Set<String> sut = new ConcurrentHashSet<String>();

    @Before
    public void assertThatSutIsEmpty() {
        assertThat(sut.isEmpty(), is(true));
    }

    @Test
    public void add_throwsExceptionIfNullAdded() {
        thrown.expect(NullPointerException.class);
        sut.add(null);
    }

    @Test
    public void add() {
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
        sut.addAll(Arrays.asList("foo", "bar", "baz", "foo"));
        assertThat(sut.size(), is(3));
    }

    @Test
    public void clear() {
        sut.add("foo");
        sut.add("bar");
        assertThat(sut.size(), is(2));

        sut.clear();
        assertThat(sut.isEmpty(), is(true));
    }

    @Test
    public void contains() {
        sut.addAll(Arrays.asList("foo", "bar", "baz"));

        assertThat(sut.contains("foo"), is(true));
        assertThat(sut.contains("bar"), is(true));
        assertThat(sut.contains("baz"), is(true));
        assertThat(sut.contains("snafu"), is(false));
    }

    @Test
    public void contains_throwsExceptionIfNullAdded() {
        thrown.expect(NullPointerException.class);
        sut.contains(null);
    }

    @Test
    public void isEmpty() {
        assertThat(sut.isEmpty(), is(true));
        sut.add("foo");
        assertThat(sut.isEmpty(), is(false));
        sut.add("bar");
        assertThat(sut.isEmpty(), is(false));
    }

    @Test
    public void size() {
        assertThat(sut.size(), is(0));
        sut.add("foo");
        assertThat(sut.size(), is(1));
        sut.add("bar");
        assertThat(sut.size(), is(2));
    }

    @Test
    public void remove() {
        sut.addAll(Arrays.asList("foo", "bar", "baz"));
        sut.remove("bar");

        assertThat(sut.contains("foo"), is(true));
        assertThat(sut.contains("bar"), is(false));
        assertThat(sut.contains("baz"), is(true));
    }

    @Test
    public void remove_throwsExceptionIfNullAdded() {
        thrown.expect(NullPointerException.class);
        sut.remove(null);
    }

    @Test
    public void iterator() {
        sut.addAll(Arrays.asList("foo", "bar", "baz"));
        final Iterator<String> iterator = sut.iterator();

        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(equalTo("baz")));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(equalTo("foo")));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is(equalTo("bar")));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test
    public void testToString() {
        assertThat(sut.toString(), is(equalTo("ConcurrentHashSet{data=[]}")));
        sut.addAll(Arrays.asList("foo", "bar", "baz"));
        assertThat(sut.toString(), is(equalTo("ConcurrentHashSet{data=[baz, foo, bar]}")));
    }
}

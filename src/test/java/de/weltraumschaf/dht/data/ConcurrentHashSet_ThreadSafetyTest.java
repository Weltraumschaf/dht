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

import edu.umd.cs.mtc.MultithreadedTestCase;
import edu.umd.cs.mtc.TestFramework;
import java.util.Set;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * Tests for thread safety of {@link ConcurrentHashSet}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ConcurrentHashSet_ThreadSafetyTest extends MultithreadedTestCase {

    private final Set<String> sut = new ConcurrentHashSet<String>();

    @Override
    public void initialize() {
        assertThat(sut.isEmpty(), is(true));
    }

    public void thread1() throws InterruptedException {
        sut.add("foo");
        sut.add("bar");
        waitForTick(2);
        sut.add("baz");
    }

    public void thread2() throws InterruptedException {
        waitForTick(1);
        assertThat(sut.contains("foo"), is(true));
        assertThat(sut.contains("bar"), is(true));
        assertThat(sut.contains("baz"), is(false));
        waitForTick(3);
        assertThat(sut.contains("foo"), is(true));
        assertThat(sut.contains("bar"), is(true));
        assertThat(sut.contains("baz"), is(true));
    }

    @Override
    public void finish() {
//        assertThat(sut.isEmpty(), is(true));
    }

    @Test
    public void runTest() throws Throwable {
        TestFramework.runOnce(new ConcurrentHashSet_ThreadSafetyTest());
    }
}

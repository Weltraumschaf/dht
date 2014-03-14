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

import org.apache.commons.lang3.Validate;

/**
 * Counts seconds from starting point.
 *
 * Provides time thread safe. {@link #start()} must be called very first. Before the clack can be used.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Clock {

    /**
     * Error message if not started.
     */
    private static final String NOT_STARTED_ERROR = "Clock not started yet! Invoke #start() first.";
    /**
     * Default source.
     */
    private static final TimeSource DEFAULT_SOURCE = new SystemTimeSource();
    /**
     * Time source provider.
     */
    private final TimeSource source;
    /**
     * Whether the clock was started or not.
     */
    private boolean started;
    /**
     * Start time.
     */
    private long startTime;

    /**
     * Creates clock with a default time source.
     */
    public Clock() {
        this(DEFAULT_SOURCE);
    }

    /**
     * Dedicated constructor.
     *
     * @param source must not be {@code null}
     */
    public Clock(final TimeSource source) {
        super();
        this.source = Validate.notNull(source);
    }

    /**
     * Starts the clock.
     *
     * Subsequent calls has no effect. Must be called first before using {@link #now()} or {@link #elapsed()}.
     *
     * @return self for chaining
     */
    public synchronized Clock start() {
        if (started) {
            return this;
        }

        startTime = source.nanoTime();
        started = true;
        return this;
    }

    /**
     * Returns the current time.
     *
     * @return current time in nanoseconds
     */
    public synchronized long now() {
        Validate.validState(started, NOT_STARTED_ERROR);
        return source.nanoTime();
    }

    /**
     * Returns the elapsed time since {@link #start()} was called first.
     *
     * @return elapsed time in nanoseconds
     */
    public synchronized long elapsed() {
        Validate.validState(started, NOT_STARTED_ERROR);
        return source.nanoTime() - startTime;
    }

    /**
     * Provides time in nanoseconds.
     */
    public static interface TimeSource {

        /**
         * Current time in nanoseconds.
         *
         * @return the current value of the time source, in nanoseconds
         */
        public long nanoTime();
    }

    /**
     * Uses {@link System#nanoTime()} as source.
     */
    private static final class SystemTimeSource implements TimeSource {

        @Override
        public long nanoTime() {
            return System.nanoTime();
        }

    }
}

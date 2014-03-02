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

import java.util.Formatter;
import org.apache.log4j.Level;

/**
 * Decorator for {@link org.apache.log4j.Logger Log4J logger}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Logger {

    /**
     * Decorated logger.
     */
    private final org.apache.log4j.Logger delegate;

    /**
     * Dedicated constructor.
     *
     * @param delegate decorated logger
     */
    public Logger(final org.apache.log4j.Logger delegate) {
        super();
        this.delegate = delegate;
    }

    /**
     * Creates new formatter.
     *
     * @see java.util.Formatter#format(java.lang.String, java.lang.Object[])
     * @param format format string
     * @param args variable number of arguments used in format
     * @return the new formatter
     */
    Formatter format(final String format, final Object... args) {
        return new Formatter().format(format, args);
    }

    /**
     * @see org.apache.log4j.Logger#setLevel(org.apache.log4j.Level)
     *
     * @param level Null values are admitted
     */
    public void setLevel(final Level level) {
        delegate.setLevel(level);
    }

    /**
     * @see org.apache.log4j.Logger#getLevel()
     *
     * @return may be {@code null}
     */
    public final Level getLevel() {
        return delegate.getLevel();
    }

    /**
     * Handle Message in format string style.
     *
     * @see org.apache.log4j.Logger#debug(java.lang.Object)
     *
     * @param format message as format string
     * @param args format string arguments
     */
    public void debug(final String format, final Object... args) {
        if (delegate.isDebugEnabled()) {
            // Do not string formatting if not enabled.
            debug(new Formatter().format(format, args).toString());
        }
    }

    /**
     * @see org.apache.log4j.Logger#debug(java.lang.Object)
     * @param message the message object to log
     */
    public void debug(final Object message) {
        delegate.debug(message);
    }

    /**
     * @see org.apache.log4j.Logger#debug(java.lang.Object, java.lang.Throwable)
     * @param message the message object to log
     * @param t the exception to log, including its stack trace
     */
    public void debug(final Object message, final Throwable t) {
        delegate.debug(message, t);
    }

    /**
     * Handle Message in format string style.
     *
     * @see org.apache.log4j.Logger#trace(java.lang.Object)
     *
     * @param format message as format string
     * @param args format string arguments
     */
    public void trace(final String format, final Object... args) {
        if (delegate.isTraceEnabled()) {
            // Do not string formatting if not enabled.
            trace(format(format, args).toString());
        }
    }

    /**
     * @see org.apache.log4j.Logger#trace(java.lang.Object)
     * @param message the message object to log
     */
    public void trace(final Object message) {
        delegate.trace(message);
    }

    /**
     * @see org.apache.log4j.Logger#trace(java.lang.Object, java.lang.Throwable)
     * @param message the message object to log
     * @param t the exception to log, including its stack trace
     */
    public void trace(final Object message, final Throwable t) {
        delegate.trace(message, t);
    }

    /**
     * Handle Message in format string style.
     *
     * @see org.apache.log4j.Logger#info(java.lang.Object)
     *
     * @param format message as format string
     * @param args format string arguments
     */
    public void info(final String format, final Object... args) {
        if (delegate.isInfoEnabled()) {
            // Do not string formatting if not enabled.
            info(format(format, args).toString());
        }
    }

    /**
     * @see org.apache.log4j.Logger#info(java.lang.Object)
     * @param message the message object to log
     */
    public void info(final Object message) {
        delegate.info(message);
    }

    /**
     * @see org.apache.log4j.Logger#info(java.lang.Object, java.lang.Throwable)
     * @param message the message object to log
     * @param t the exception to log, including its stack trace
     */
    public void info(final Object message, final Throwable t) {
        delegate.info(message, t);
    }

    /**
     * Handle Message in format string style.
     *
     * @see org.apache.log4j.Logger#warn(java.lang.Object)
     *
     * @param format message as format string
     * @param args format string arguments
     */
    public void warn(final String format, final Object... args) {
        if (delegate.isEnabledFor(Level.WARN)) {
            // Do not string formatting if not enabled.
            warn(format(format, args).toString());
        }
    }

    /**
     * @see org.apache.log4j.Logger#warn(java.lang.Object)
     * @param message the message object to log
     */
    public void warn(final Object message) {
        delegate.warn(message);
    }

    /**
     * @see org.apache.log4j.Logger#warn(java.lang.Object, java.lang.Throwable)
     * @param message the message object to log
     * @param t the exception to log, including its stack trace
     */
    public void warn(final Object message, final Throwable t) {
        delegate.warn(message, t);
    }

    /**
     * Handle Message in format string style.
     *
     * @see org.apache.log4j.Logger#error(java.lang.Object)
     *
     * @param format message as format string
     * @param args format string arguments
     */
    public void error(final String format, final Object... args) {
        if (delegate.isEnabledFor(Level.ERROR)) {
            // Do not string formatting if not enabled.
            error(format(format, args).toString());
        }
    }

    /**
     * @see org.apache.log4j.Logger#error(java.lang.Object)
     * @param message the message object to log
     */
    public void error(final Object message) {
        delegate.error(message);
    }

    /**
     * @see org.apache.log4j.Logger#error(java.lang.Object, java.lang.Throwable)
     * @param message the message object to log
     * @param t the exception to log, including its stack trace
     */
    public void error(final Object message, final Throwable t) {
        delegate.error(message, t);
    }

    /**
     * Handle Message in format string style.
     *
     * @see org.apache.log4j.Logger#fatal(java.lang.Object)
     *
     * @param format message as format string
     * @param args format string arguments
     */
    public void fatal(final String format, final Object... args) {
        if (delegate.isEnabledFor(Level.FATAL)) {
            // Do not string formatting if not enabled.
            fatal(format(format, args).toString());
        }
    }

    /**
     * @see org.apache.log4j.Logger#fatal(java.lang.Object)
     * @param message the message object to log
     */
    public void fatal(final Object message) {
        delegate.fatal(message);
    }

    /**
     * @see org.apache.log4j.Logger#fatal(java.lang.Object, java.lang.Throwable)
     * @param message the message object to log
     * @param t the exception to log, including its stack trace
     */
    public void fatal(final Object message, final Throwable t) {
        delegate.fatal(message, t);
    }
}

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
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Logger}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class LoggerTest {
    private static final String FORMAT = "test: %s";
    private static final String ARG_OBJECT = "foobar";

    private final org.apache.log4j.Logger delegate = mock(org.apache.log4j.Logger.class);
    private final Logger sut = new Logger(delegate);

    @Test
    public void format() {
        assertThat(sut.format("").toString(), is(""));
        assertThat(sut.format("foobar").toString(), is("foobar"));
        assertThat(sut.format("foo%s", "bar").toString(), is("foobar"));
    }

    @Test
    public void setAndGetLevel() {
        final org.apache.log4j.Logger innerDelegate = org.apache.log4j.Logger.getLogger(this.getClass());
        final Logger innerSut = new Logger(innerDelegate);
        final Level level = Level.OFF;
        innerSut.setLevel(level);
        // Testing this via mock verification not possibale because Logger#getLevel is final.
        assertThat(innerSut.getLevel(), is(sameInstance(level)));
    }

    @Test
    public void testSetLevel() {
        final Level level = Level.OFF;
        sut.setLevel(level);
        verify(delegate, times(1)).setLevel(level);
    }

    @Test
    public void testDebug_formatString_isEnabled() {
        when(delegate.isDebugEnabled()).thenReturn(Boolean.TRUE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.debug(FORMAT, ARG_OBJECT);
        verify(delegate, times(1)).debug(fmt.toString());
    }

    @Test
    public void testDebug_formatString_isNotEnabled() {
        when(delegate.isDebugEnabled()).thenReturn(Boolean.FALSE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.debug(FORMAT, ARG_OBJECT);
        verify(delegate, never()).debug(fmt.toString());
    }

    @Test
    public void testDebug_Object() {
        final String msg = "msg";
        sut.debug(msg);
        verify(delegate, times(1)).debug(msg);
    }

    @Test
    public void testDebug_Object_Throwable() {
        final String msg = "msg";
        final Throwable t = new Throwable();
        sut.debug(msg, t);
        verify(delegate, times(1)).debug(msg, t);
    }

    @Test
    public void testTrace_FormatString_isEnabled() {
        when(delegate.isTraceEnabled()).thenReturn(Boolean.TRUE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.trace(FORMAT, ARG_OBJECT);
        verify(delegate, times(1)).trace(fmt.toString());
    }

    @Test
    public void testTrace_FormatString_isNotEnabled() {
        when(delegate.isTraceEnabled()).thenReturn(Boolean.FALSE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.trace(FORMAT, ARG_OBJECT);
        verify(delegate, never()).trace(fmt.toString());
    }

    @Test
    public void testTrace_Object() {
        final String msg = "msg";
        sut.trace(msg);
        verify(delegate, times(1)).trace(msg);
    }

    @Test
    public void testTrace_Object_Throwable() {
        final String msg = "msg";
        final Throwable t = new Throwable();
        sut.trace(msg, t);
        verify(delegate, times(1)).trace(msg, t);
    }

    @Test
    public void testInfo_formatString_isEnabled() {
        when(delegate.isInfoEnabled()).thenReturn(Boolean.TRUE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.info(FORMAT, ARG_OBJECT);
        verify(delegate, times(1)).info(fmt.toString());
    }

    @Test
    public void testInfo_formatString_isNotEnabled() {
        when(delegate.isInfoEnabled()).thenReturn(Boolean.FALSE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.info(FORMAT, ARG_OBJECT);
        verify(delegate, never()).info(fmt.toString());
    }

    @Test
    public void testInfo_Object() {
        final String msg = "msg";
        sut.info(msg);
        verify(delegate, times(1)).info(msg);
    }

    @Test
    public void testInfo_Object_Throwable() {
        final String msg = "msg";
        final Throwable t = new Throwable();
        sut.info(msg, t);
        verify(delegate, times(1)).info(msg, t);
    }

    @Test
    public void testWarn_formatString_isEnabled() {
        when(delegate.isEnabledFor(Level.WARN)).thenReturn(Boolean.TRUE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.warn(FORMAT, ARG_OBJECT);
        verify(delegate, times(1)).warn(fmt.toString());
    }

    @Test
    public void testWarn_formatString_isNotEnabled() {
        when(delegate.isEnabledFor(Level.WARN)).thenReturn(Boolean.FALSE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.warn(FORMAT, ARG_OBJECT);
        verify(delegate, never()).warn(fmt.toString());
    }

    @Test
    public void testWarn_Object() {
        final String msg = "msg";
        sut.warn(msg);
        verify(delegate, times(1)).warn(msg);
    }

    @Test
    public void testWarn_Object_Throwable() {
        final String msg = "msg";
        final Throwable t = new Throwable();
        sut.warn(msg, t);
        verify(delegate, times(1)).warn(msg, t);
    }

    @Test
    public void testError_formatString_isEnabled() {
        when(delegate.isEnabledFor(Level.ERROR)).thenReturn(Boolean.TRUE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.error(FORMAT, ARG_OBJECT);
        verify(delegate, times(1)).error(fmt.toString());
    }

    @Test
    public void testError_formatString_isNotEnabled() {
        when(delegate.isEnabledFor(Level.ERROR)).thenReturn(Boolean.FALSE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.error(FORMAT, ARG_OBJECT);
        verify(delegate, never()).error(fmt.toString());
    }

    @Test
    public void testError_Object() {
        final String msg = "msg";
        sut.error(msg);
        verify(delegate, times(1)).error(msg);
    }

    @Test
    public void testError_Object_Throwable() {
        final String msg = "msg";
        final Throwable t = new Throwable();
        sut.error(msg, t);
        verify(delegate, times(1)).error(msg, t);
    }

    @Test
    public void testFatal_formatString_isEnabled() {
        when(delegate.isEnabledFor(Level.FATAL)).thenReturn(Boolean.TRUE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.fatal(FORMAT, ARG_OBJECT);
        verify(delegate, times(1)).fatal(fmt.toString());
    }

    @Test
    public void testFatal_formatString_isNotEnabled() {
        when(delegate.isEnabledFor(Level.FATAL)).thenReturn(Boolean.FALSE);
        final Formatter fmt = sut.format(FORMAT, ARG_OBJECT);
        sut.fatal(FORMAT, ARG_OBJECT);
        verify(delegate, never()).fatal(fmt.toString());
    }

    @Test
    public void testFatal_Object() {
        final String msg = "msg";
        sut.fatal(msg);
        verify(delegate, times(1)).fatal(msg);
    }

    @Test
    public void testFatal_Object_Throwable() {
        final String msg = "msg";
        final Throwable t = new Throwable();
        sut.fatal(msg, t);
        verify(delegate, times(1)).fatal(msg, t);
    }

}

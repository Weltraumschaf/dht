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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.Layout;

/**
 * Log4J layout for Tacoli messages.
 *
 * Format: {@code ISO-DATE\tLEVEL\tMESSAGE}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class TacoliLayout extends Layout {

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mmZ";
    /**
     * Default buffer size.
     */
    private final int DEFAULT_SIZE = 256;
    /**
     * Upper limit for buffer.
     */
    private final int UPPER_LIMIT = 2048;
    /**
     * Message buffer.
     */
    private StringBuffer buffer = new StringBuffer(DEFAULT_SIZE);

    @Override
    public String format(final LoggingEvent event) {
        Validate.notNull(event, "Parameter >event< must not be null!");
        initBuffer();

        buffer.append(formatTime(event.getTimeStamp())).append(Tacoli.COLUMN_DELIMITER);
        buffer.append(event.getLevel()).append(Tacoli.COLUMN_DELIMITER);
        buffer.append(event.getMessage()).append(Tacoli.NEW_LINE);

        return buffer.toString();
    }

    /**
     * Reinitializes buffer if {@link #UPPER_LIMIT} is exceeded.
     */
    private void initBuffer() {
        // Reset working buffer. If the buffer is too large, then we need a new
        // one in order to avoid the penalty of creating a large array.
        if (buffer.capacity() > UPPER_LIMIT) {
            buffer = new StringBuffer(DEFAULT_SIZE);
        } else {
            buffer.setLength(0);
        }
    }

    @Override
    public boolean ignoresThrowable() {
        return true;
    }

    @Override
    public void activateOptions() {
        // Nothing to do here.
    }

    private String formatTime(final long timeStamp) {
        final DateFormat df = new SimpleDateFormat(TIMESTAMP_FORMAT);
        return df.format(new Date(timeStamp));
    }

}

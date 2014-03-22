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

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 * A Tacoli message contains of a collection of key value pairs separated by {@link Tacoli#COLUMN_DELIMITER}.
 *
 * This class is not thread safe.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class TacoliMessage {

    /**
     * Standard key for a pure text log message.
     */
    public static final String KEY_MESSAGE = "message";

    /**
     * Collects the entries.
     */
    private final List<Entry> data = new ArrayList<Entry>();

    /**
     * Convenience method to create an entry with the key {@link #KEY_MESSAGE}.
     *
     * @param mesage must not be {@code null} or empty
     */
    public void message(final String mesage) {
        add(KEY_MESSAGE, Validate.notEmpty(mesage, "Parameter >mesage< must not be null or empty!"));
    }

    /**
     * Adds an message entry.
     *
     * @param key must not be {@code null} or empty
     * @param value must not be {@code null}
     */
    public void add(final String key, final Object value) {
        data.add(
            new Entry(
                Validate.notEmpty(key, "Parameter >key< must not be null or empty!"),
                Validate.notNull(value, "Parameter >value< must not be null!")));
    }

    /**
     * Formats the message.
     *
     * Entries are formated like {@code key=value}. The entries are concatenated with {@link Tacoli#COLUMN_DELIMITER} as
     * separator.
     *
     * @return never {@code null}, maybe empty
     */
    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        boolean firstEntry = true;

        for (final Entry entry : data) {
            if (!firstEntry) {
                buffer.append(Tacoli.COLUMN_DELIMITER);
            }

            buffer.append(entry.toString());
            firstEntry = false;
        }

        return buffer.toString();
    }

    /**
     * A message entry.
     */
    private static final class Entry {

        /**
         * Key of entry.
         */
        private final String key;
        /**
         * Entry value.
         */
        private final Object value;

        /**
         *
         * @param key must not be {@code null} or empty
         * @param value must not be {@code null}
         */
        public Entry(final String key, final Object value) {
            super();
            this.key = Validate.notEmpty(key, "Parameter >key< must not be null or empty!");
            this.value = Validate.notNull(value, "Parameter >value< must not be null!");
        }

        /**
         * Formats an entry as an concatenation of {@link String#toString() key} + {@link Tacoli#KEY_VALUE_DELIMITER} + {@link Object#toString() value}
         *
         * @return never {@code null} or empty
         */
        @Override
        public String toString() {
            return new StringBuilder().append(key).append(Tacoli.KEY_VALUE_DELIMITER).append(value).toString();
        }

    }
}

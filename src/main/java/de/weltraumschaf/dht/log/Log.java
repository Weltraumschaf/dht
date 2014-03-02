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

/**
 * Get Log4J loggers.
 *
 * Usage:
 * <pre>
 * private final Logger log = Log.getLogger(this);
 * //...
 * log.trace("Trace");
 * log.debug("Debug");
 * log.info("Info");
 * log.warn("Warn");
 * log.error("Error");
 * log.fatal("Fatal");
 * </pre>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Log {

    /**
     * Hidden for pure static class.
     */
    private Log() {
        super();
    }

    /**
     * Get logger for class type of an object.
     *
     * @param object to create logger for
     * @return new logger
     */
    public static Logger getLogger(final Object object) {
        return getLogger(object.getClass());
    }

    /**
     * Get logger for class type.
     *
     * @param clazz to create logger for
     * @return new logger
     */
    public static Logger getLogger(final Class<?> clazz) {
        return new Logger(org.apache.log4j.Logger.getLogger(clazz));
    }

}

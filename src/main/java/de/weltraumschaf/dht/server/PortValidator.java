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
package de.weltraumschaf.dht.server;

/**
 * Validates that a given number is a valid port.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class PortValidator {

    /**
     * All ports below 1024 are privileged.
     */
    private static final int MIN = 1025;
    /**
     * Max is 2^16.
     */
    private static final int MAX = 65535;

    /**
     * Validates if given number is a valid TCP port for this application.
     *
     * @param port any number
     * @return {@code true} if valid, else {@code false}
     */
    public static final boolean isValid(final int port) {
        if (isToSmall(port)) {
            return false;
        }

        if (isToLarge(port)) {
            return false;
        }

        return true;
    }

    /**
     * Indicates if a port is too small.
     *
     * @param port port any number
     * @return {@code true} if port is too small, else {@code false}
     */
    public static final boolean isToSmall(final int port) {
        return port < MIN;
    }

    /**
     * Indicates if a port is too large.
     *
     * @param port port any number
     * @return {@code true} if port is too lage, else {@code false}
     */
    public static final boolean isToLarge(final int port) {
        return port > MAX;
    }

    /**
     * Gives a string describing the valid range.
     *
     * @return never {@code null} or empty
     */
    public static final String range() {
        return MIN + ".." + MAX;
    }
}

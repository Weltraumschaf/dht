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

package de.weltraumschaf.dht.shell;

import org.apache.commons.lang3.Validate;

/**
 * Signals bad command argument.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CommandArgumentExcpetion extends IllegalArgumentException {

    /**
     * Dedicated constructor.
     *
     * @param message must not be {@code null} or empty
     */
    public CommandArgumentExcpetion(final String message) {
        super(Validate.notEmpty(message, "Parameter >s< must not be null or empty!"));
    }

}

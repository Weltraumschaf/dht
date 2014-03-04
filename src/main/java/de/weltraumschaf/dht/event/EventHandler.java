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
package de.weltraumschaf.dht.event;

import de.weltraumschaf.commons.IO;
import org.apache.commons.lang3.Validate;

/**
 * Handles events emitted by {@link Observable}.
 *
 * @author "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
public class EventHandler implements Observer {

    /**
     * Used to print event to output.
     */
    private final IO io;

    /**
     * Dedicated constructor.
     *
     * @param io must not be {@code null}
     */
    public EventHandler(final IO io) {
        super();
        this.io = Validate.notNull(io, "Parameter >io< must not be null!");
    }

    @Override
    public void update(final Event event) {

    }

}

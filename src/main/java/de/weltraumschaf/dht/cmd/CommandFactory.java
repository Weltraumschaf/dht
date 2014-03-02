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
package de.weltraumschaf.dht.cmd;

import de.weltraumschaf.commons.IO;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.commons.shell.ShellCommand;

/**
 * Factory to create command objects depending on the parsed {@link ShellCommand}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CommandFactory {

    /**
     * Shell I/O.
     */
    private final IO io;

    /**
     * Dedicated constructor.
     *
     * @param io shell I/O used by commands
     */
    public CommandFactory(final IO io) {
        super();
        this.io = io;
    }

    /**
     * Create command instances according to the parsed shell command.
     *
     * @param shellCmd used to determine appropriate command
     * @return command object // CHECKSTYLE:OFF
     * @throws IllegalArgumentException if, can't create command of bad main or sub command type // CHECKSTYLE:ON
     */
    public Command newCommand(final ShellCommand shellCmd) {
        Command cmd = null;

        return cmd;
    }

}

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
import de.weltraumschaf.commons.shell.Token;
import java.util.List;

/**
 * Base implementation for commands.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseCommand implements Command {

    /**
     * Arguments for the command.
     */
    private final List<Token> arguments;

    /**
     * Shell I/O.
     */
    private final IO io;

    /**
     * Dedicated constructor.
     *
     * @param io shell I/O
     * @param arguments parsed shell arguments
     */
    public BaseCommand(final IO io, final List<Token> arguments) {
        super();
        this.io = io;
        this.arguments = arguments;
    }

    /**
     * Getter for sub classes.
     *
     * @return shellI/O
     */
    IO getIo() {
        return io;
    }

    /**
     * Getter for sub classes.
     *
     * @return parsed shell arguments, may return empty list
     */
    List<Token> getArguments() {
        return arguments;
    }

}

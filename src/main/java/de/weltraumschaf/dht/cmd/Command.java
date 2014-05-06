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

import de.weltraumschaf.commons.shell.SubCommandType;
import de.weltraumschaf.commons.token.Token;
import de.weltraumschaf.dht.ApplicationContext;
import java.util.List;

/**
 * Interface for shell commands.
 *
 * Based on command pattern.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Command extends Describable {

    /**
     * Executes the command.
     */
    void execute();

    /**
     * Injection point for arguments.
     *
     * Implementations must make a unmodifiable copy.
     *
     * @param arguments must not be {@code null}
     */
    void setArguments(List<Token<?>> arguments);

    /**
     * Injection point for invoking application.
     *
     * @param app must not be {@code null}
     */
    void setApplicationContext(final ApplicationContext app);

    void setSubCommandType(SubCommandType subCommand);
}

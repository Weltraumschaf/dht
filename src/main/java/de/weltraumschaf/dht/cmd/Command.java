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

import de.weltraumschaf.commons.shell.Token;
import de.weltraumschaf.dht.Application;
import java.util.List;

/**
 * Interface for shell commands.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Command {

    /**
     * Executes the command.
     */
    void execute();
    void setArguments(List<Token> arguments);
    void setApp(final Application app);
}

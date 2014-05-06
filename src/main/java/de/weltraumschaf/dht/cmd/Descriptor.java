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

import de.weltraumschaf.dht.shell.MainCommand;

/**
 * Describes a command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Descriptor extends Comparable<Descriptor> {

    /**
     * Get the command type associated with described command.
     *
     * @return never {@code null}
     */
    MainCommand getCommand();
    /**
     * Get usage string for help.
     *
     * @return never {@code null} or empty
     */
    String getUsage();
    /**
     * Get description string for help.
     *
     * @return never {@code null} or empty
     */
    String getHelpDescription();

}

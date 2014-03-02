/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.dht.shell;

import com.google.common.collect.Lists;
import de.weltraumschaf.commons.shell.MainCommandType;
import java.util.Collections;
import java.util.List;

/**
 * Enumerates the available commands.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum NeuronMainType implements MainCommandType {

    /**
     * Help command.
     */
    HELP("help"),
    /**
     * Exit command.
     */
    EXIT("exit");

    /**
     * Literal command string used in shell.
     */
    private final String literal;

    /**
     * Initialize name.
     *
     * @param name literal shell command string
     */
    private NeuronMainType(final String name) {
        this.literal = name;
    }

    @Override
    public String toString() {
        return literal;
    }

    /**
     * Returns the list of available sub commands.
     *
     * If a command does not support sub commands an empty list is returned.
     *
     * @return list of subtypes
     */
    List<NeuronSubType> subCommands() {
        List<NeuronSubType> subCommands;

        switch (this) {
            default:
                subCommands = Collections.emptyList();
        }

        return subCommands;
    }

}

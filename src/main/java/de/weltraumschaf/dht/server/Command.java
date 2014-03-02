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
 * Encapsulates a single task command.
 *
 * @param <T> type of command result.
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Command<T> {

    /**
     * Executes the command.
     *
     * @throws Exception if errors happened
     */
    void execute() throws Exception;

    /**
     * Returns the result of the executed command.
     *
     * @return May return null if called before {@link #execute()}
     */
    T getResult();
}

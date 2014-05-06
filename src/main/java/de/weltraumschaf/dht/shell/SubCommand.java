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

import de.weltraumschaf.commons.shell.SubCommandType;

/**
 * Enumerates the optional subcommands.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum SubCommand implements SubCommandType {

    /**
     * No sub command.
     */
    NONE(""),

    HELP_BOOTSTRAP(MainCommand.BOOTSTRAP.toString()),
    HELP_EXIT(MainCommand.EXIT.toString()),
    HELP_INBOX(MainCommand.INBOX.toString()),
    HELP_SEND(MainCommand.SEND.toString()),
    HELP_START(MainCommand.START.toString()),
    HELP_STOP(MainCommand.STOP.toString()),
    HELP_STATUS(MainCommand.STATUS.toString()),

    INBOX_REMOVE("remove"),
    INBOX_SHOW("show"),
    INBOX_ANSWER("answer");

    /**
     * Literal sub command string used in shell.
     */
    private final String literal;

    /**
     * Initialize name.
     *
     * @param literal literal shell command string
     */
    private SubCommand(final String literal) {
        this.literal = literal;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    @Override
    public String toString() {
        return getLiteral();
    }

}

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

import de.weltraumschaf.dht.Application;

/**
 * Prints {@link #HELP} into the shell.
 *
 * @author @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Help extends BaseCommand {

    /**
     * Help message for shell users.
     */
    private static final String HELP
            = "This is the %s interactive shell version %s.%n%n"

            + "Available commands:%n%n"

            + "  help                           Show all available commands.%n"
            + "  status                         Show application status information.%n"
            + "  exit                           Exit the interactive shell.%n%n"

            + "  start                          Starts the server.%n"
            + "  stop                           Stops the server.%n%n"

            + "  send <host> <port> <message>   Sends <message> to <host:port>.%n"
            + "                                 The message must be encapsulated in quotes if it%n"
            + "                                 has more than one word.%n%n";

    @Override
    public void execute() {
        println(formatHelp());
    }

    private String formatHelp() {
        return String.format(HELP, Application.NAME, getApplication().getVersion());
    }

}

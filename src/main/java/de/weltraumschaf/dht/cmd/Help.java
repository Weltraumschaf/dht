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
 * Prints {@link #HELP} into the shell.
 *
 * @author @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Help extends BaseCommand {

    /**
     * Help message for shell users.
     */
    private static final String HELP
            = "This is the %s interactive shell version %s.%n%n"

            + "Available commands:%n%n"

            + "  help   Show all available commands.%n"
            + "  exit   Exit the interactive shell.%n%n";

    /**
     * Dedicated constructor.
     *
     * @param app invoking application
     * @param arguments command arguments
     */
    public Help(final Application app, final List<Token> arguments) {
        super(app, arguments);
    }

    @Override
    public void execute() {
        getApplication().getIoStreams().println(formatHelp());
    }

    private String formatHelp() {
        return String.format(HELP, Application.NAME, getApplication().getVersion());
    }

}

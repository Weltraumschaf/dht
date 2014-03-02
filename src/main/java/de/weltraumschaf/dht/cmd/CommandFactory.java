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

import de.weltraumschaf.commons.shell.ShellCommand;
import de.weltraumschaf.dht.Application;
import de.weltraumschaf.dht.shell.CommandMainType;
import static de.weltraumschaf.dht.shell.CommandMainType.EXIT;
import static de.weltraumschaf.dht.shell.CommandMainType.HELP;
import org.apache.commons.lang3.Validate;

/**
 * Factory to create command objects depending on the parsed {@link ShellCommand}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CommandFactory {

    /**
     * Invoking application.
     */
    private final Application app;

    /**
     * Dedicated constructor.
     *
     * @param app the invoking application
     */
    public CommandFactory(final Application app) {
        super();
        this.app = Validate.notNull(app, "Parameter >app< must not be null!");
    }

    /**
     * Create command instances according to the parsed shell command.
     *
     * @param shellCmd used to determine appropriate command
     * @return command object // CHECKSTYLE:OFF
     * @throws IllegalArgumentException if, can't create command of bad main or sub command type // CHECKSTYLE:ON
     */
    public Command newCommand(final ShellCommand shellCmd) {
        Command cmd;

        switch ((CommandMainType) shellCmd.getCommand()) {
            case EXIT:
                cmd = new Exit(app, shellCmd.getArguments());
                break;
            case HELP:
                cmd = new Help(app, shellCmd.getArguments());
                break;
            case STATUS:
                cmd = new Status(app, shellCmd.getArguments());
                break;
            case START:
                cmd = new Start(app, shellCmd.getArguments());
                break;
            case STOP:
                cmd = new Stop(app, shellCmd.getArguments());
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported main command type '%s'!",
                        shellCmd.getCommand()));
        }

        return cmd;
    }

}

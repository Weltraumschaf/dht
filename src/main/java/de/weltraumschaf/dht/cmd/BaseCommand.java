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
import org.apache.commons.lang3.Validate;

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
     * Invoking application.
     */
    private final Application app;

    /**
     * Dedicated constructor.
     *
     * @param app invoking application
     * @param arguments parsed shell arguments
     */
    public BaseCommand(final Application app, final List<Token> arguments) {
        super();
        this.app = Validate.notNull(app, "Parameter >app< must not be null!");
        this.arguments = Validate.notNull(arguments, "Parameter >arguments< must not be null!");
    }

    /**
     * Getter for sub classes.
     *
     * @return shellI/O
     */
    Application getApplication() {
        return app;
    }

    /**
     * Getter for sub classes.
     *
     * @return parsed shell arguments, may return empty list
     */
    List<Token> getArguments() {
        return arguments;
    }

    String formatListenedAddress() {
        return String.format("%s:%d",
                getApplication().getOptions().getHost(),
                getApplication().getOptions().getPort());
    }

    void println(final String msg) {
        getApplication().getIoStreams().println(msg);
    }

    void errorln(final String msg) {
        println("Error: " + msg);
    }

    void printStackTrace(final Throwable ex) {
        getApplication().getIoStreams().printStackTrace(ex);
    }

    boolean isDebug() {
        return getApplication().getOptions().isDebug();
    }

    String nl() {
        return Application.NL;
    }
}

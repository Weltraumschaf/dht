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
import java.util.Collections;
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
    private List<Token> arguments;

    /**
     * Invoking application.
     */
    private Application app;

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

    @Override
    public void setArguments(final List<Token> arguments) {
        this.arguments = Collections.unmodifiableList(Validate.notNull(arguments, "Parameter >arguments< must not be null!"));
    }

    @Override
    public void setApp(final Application app) {
        this.app = Validate.notNull(app, "Parameter >app< must not be null!");
    }

    /**
     * Returns formatted host address the application is configured by CLI options.
     *
     * @return never {@code null} or empty
     */
    String formatListenedAddress() {
        return String.format("%s:%d",
                getApplication().getOptions().getHost(),
                getApplication().getOptions().getPort());
    }

    /**
     * Delegates to the STDOUT of the application.
     *
     * @param msg may be {@code null} or empty
     */
    void println(final String msg) {
        getApplication().getIoStreams().println(msg);
    }

    /**
     * Delegates to the STDOUT of the application.
     *
     * @param msg may be {@code null} or empty
     */
    void print(final String msg) {
        getApplication().getIoStreams().print(msg);
    }

    /**
     * Delegates to the STDERR of the application.
     *
     * @param msg may be {@code null} or empty
     */
    void errorln(final String msg) {
        println("Error: " + msg);
    }

    /**
     * Prints stacktrace to the STDOUT of the application.
     *
     * @param msg may be {@code null} or empty
     */
    void printStackTrace(final Throwable ex) {
        getApplication().getIoStreams().printStackTrace(ex);
    }

    /**
     * Whether debug option is enabled by the applications CLI options.
     *
     * @return {@code true} if debug is enabled, else {@code false}
     */
    boolean isDebug() {
        return getApplication().getOptions().isDebug();
    }

    /**
     * Get the application wide new line string.
     *
     * @return never {@code null} or empty
     */
    String nl() {
        return Application.NL;
    }
}

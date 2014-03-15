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

import de.weltraumschaf.commons.shell.SubCommandType;
import de.weltraumschaf.commons.shell.Token;
import de.weltraumschaf.dht.Application;
import de.weltraumschaf.dht.ApplicationContext;
import de.weltraumschaf.dht.net.NetworkAddress;
import de.weltraumschaf.dht.shell.CommandSubType;
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
    private ApplicationContext application;
    /**
     * Holds the subcommand.
     *
     * Default is {@link CommandSubType#NONE}.
     */
    private SubCommandType subCommand = CommandSubType.NONE;

    /**
     * Getter for sub classes.
     *
     * @return shellI/O
     */
    ApplicationContext getApplicationContext() {
        return application;
    }

    /**
     * Getter for sub classes.
     *
     * @return parsed shell arguments, may return empty list
     */
    List<Token> getArguments() {
        return arguments;
    }

    SubCommandType getSubCommand() {
        return subCommand;
    }

    @Override
    public void setArguments(final List<Token> arguments) {
        this.arguments = Collections.unmodifiableList(Validate.notNull(arguments, "Parameter >arguments< must not be null!"));
    }

    @Override
    public void setApplicationContext(final ApplicationContext applocation) {
        this.application = Validate.notNull(applocation, "Parameter >applocation< must not be null!");
    }

    @Override
    public void setSubCommandType(final SubCommandType subCommand) {
        this.subCommand = Validate.notNull(subCommand, "Parameter >subCommand< must not be null!");
    }


    NetworkAddress newAddress(final String host, final int port) {
        return new NetworkAddress(host, port);
    }

    NetworkAddress newLocalAddress() {
        return newAddress(
                getApplicationContext().getOptions().getHost(),
                getApplicationContext().getOptions().getPort());
    }

    /**
     * Returns formatted host address the application is configured by CLI options.
     *
     * @return never {@code null} or empty
     */
    String formatLocalAddress() {
        return String.format("%s:%d",
                getApplicationContext().getOptions().getHost(),
                getApplicationContext().getOptions().getPort());
    }

    /**
     * Delegates to the STDOUT of the application.
     *
     * @param msg may be {@code null} or empty
     */
    void println(final String msg) {
        getApplicationContext().getIoStreams().println(msg);
    }

    /**
     * Prints empty line on STDOUT.
     */
    void println() {
        println("");
    }

    /**
     * Delegates to the STDOUT of the application.
     *
     * @param msg may be {@code null} or empty
     */
    void print(final String msg) {
        getApplicationContext().getIoStreams().print(msg);
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
     * Prints stack trace to the STDOUT of the application.
     *
     * @param msg may be {@code null} or empty
     */
    void printStackTrace(final Throwable ex) {
        getApplicationContext().getIoStreams().printStackTrace(ex);
    }

    /**
     * Whether debug option is enabled by the application CLI options.
     *
     * @return {@code true} if debug is enabled, else {@code false}
     */
    boolean isDebug() {
        return getApplicationContext().getOptions().isDebug();
    }

    /**
     * Get the application wide new line string.
     *
     * @return never {@code null} or empty
     */
    String nl() {
        return ApplicationContext.NL;
    }
}

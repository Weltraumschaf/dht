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
package de.weltraumschaf.dht;

import de.weltraumschaf.commons.IOStreams;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.dht.msg.MessageBox;
import de.weltraumschaf.dht.server.Server;

/**
 * Interface of the application.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Application {

    /**
     * Name of application.
     */
    public static final String NAME = "dht";
    /**
     * Used new line character.
     */
    public static final String NL = "\n";
    /**
     * The encoding used by this application for any I/O.
     */
    public static final String ENCODING = "utf-8";

    /**
     * Get the applications I/O streams.
     *
     * @return never {@code null}
     */
    IOStreams getIoStreams();

    /**
     * Get the applications version.
     *
     * @return never {@code null}
     */
    Version getVersion();

    /**
     * Get the applications command line options.
     *
     * @return never {@code null}
     */
    CliOptions getOptions();

    Server getServer();

    MessageBox getInbox();
}

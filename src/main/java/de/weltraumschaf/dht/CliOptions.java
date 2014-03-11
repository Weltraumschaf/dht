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

import com.beust.jcommander.Parameter;

/**
 * Immutable container to hold the command line options.
 *
 * TODO Add validation for port and host.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CliOptions {

    /**
     * Default host to listen on, if not given as CLI option.
     */
    private static final String DEFAULT_HOST = "127.0.0.1";
    /**
     * Default port to listen on, if not given as CLI option.
     */
    public static final int DEFAULT_PORT = 8888;

    /**
     * Port to listen on.
     */
    @Parameter(names = "-p", description = "Port to listen on.")
    private int port = DEFAULT_PORT;

    /**
     * Host name to listen on.
     */
    @Parameter(names = "-h", description = "Host address to bind socket on.")
    private String host = DEFAULT_HOST;

    /**
     * If version information is requested by user.
     */
    @Parameter(names = "-v", description = "Show version info.")
    private boolean version;

    /**
     * If debug information is requested by user.
     */
    @Parameter(names = "-d", description = "Shows debug information.")
    private boolean debug;

    /**
     * If help is requested by user.
     */
    @Parameter(names = "--help", description = "Show this help message.", help = true)
    private boolean help;

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public boolean isHelp() {
        return help;
    }

    public boolean isVersion() {
        return version;
    }

    public boolean isDebug() {
        return debug;
    }

}

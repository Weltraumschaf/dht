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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class CliOptions {

    @Parameter(names = "-p", description = "Port to listen on.")
    private int port;
    @Parameter(names = "-h", description = "Host address to bind socket on.")
    private String host;
    @Parameter(names = "-v", description = "Show version info.")
    private boolean version;
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

}

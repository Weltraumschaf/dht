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
     * A small number representing the degree of parallelism in network calls, usually 3.
     */
    public static final int DEFAULT_ALPHA = 3;
    /**
     * The size in bits of the keys used to identify nodes and store and retrieve data; in basic Kademlia this is 160,
     * the length of a SHA-1 digest (hash).
     *
     * In DHT it is 128 bits, the length of an UUID.
     */
    private static final int DEFAULT_B = 128;
    /**
     * k is the maximum number f contacts stored in a bucket this is normally 20.
     */
    private static final int DEFAULT_K = 20;
    /**
     * The time in seconds after which a key/value pair expires, this is a time-to-live (TTL) from the original
     * publication date.
     */
    private static final int DEFAULT_T_EXPIRES = 86_400;
    /**
     * Time in seconds after which an otherwise unaccessed bucket must be refreshed.
     */
    private static final int DEFAULT_T_REFRESH = 3_600;
    /**
     * Interval in seconds between Kademlia replication events,when a node is required to publish the entire database.
     */
    private static final int DEFAULT_T_REPLICATION = 3_600;
    /**
     * The time in seconds after which the original publisher must republish a key/value pair.
     */
    private static final int DEFAULT_T_REPUBLISH = 86_400;

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

    public int getAlpha() {
        return DEFAULT_ALPHA;
    }

    public int getB() {
        return DEFAULT_B;
    }

    public int getK() {
        return DEFAULT_K;
    }

    public int getTExpires() {
        return DEFAULT_T_EXPIRES;
    }

    public int getTRefresh() {
        return DEFAULT_T_REFRESH;
    }

    public int getTReplication() {
        return DEFAULT_T_REPLICATION;
    }

    public int getTRepublish() {
        return DEFAULT_T_REPUBLISH;
    }

}

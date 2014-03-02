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

package de.weltraumschaf.dht.server;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Server {

    private static final int MAX_PORT = 65535;
    private String host = null;
    private int port = -1;
    private boolean running;

    public void setHost(final String host) {
        this.host = Validate.notEmpty(host, "Parameter >host< must not be null or empty!");
    }

    public void setPort(final int port) {
        Validate.isTrue(port > 0, "Parameter >port< must be greater that 0!");
        Validate.isTrue(port < MAX_PORT, "Parameter >port< must be less that " + MAX_PORT + "!");
        this.port = port;
    }

    public void start() {
        if (null == host) {
            throw new IllegalStateException("Host not set!");
        }

        if (-1 == port) {
            throw new IllegalStateException("Port not set!");
        }

        running = true;
    }

    public void stop() {
        running = false;
    }

}

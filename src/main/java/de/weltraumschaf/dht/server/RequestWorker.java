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

import de.weltraumschaf.commons.IO;
import java.net.Socket;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class RequestWorker implements Task {

    private final ConnectionQueue queue;
    private final IO io;
    private volatile boolean stop = false;
    private volatile boolean ready = false;

    public RequestWorker(final ConnectionQueue queue, final IO io) {
        super();
        this.queue = Validate.notNull(queue, "Parameter >queue< must not be null!");
        this.io = Validate.notNull(io, "Parameter >io< must not be null!");
    }

    @Override
    public void run() {
        while (true) {
            if (!queue.isEmpty()) {
                handleRequset(queue.get());
            }

            if (stop && queue.isEmpty()) {
                io.println("Stop request worker task " + hashCode() + ".");
                ready = true;
                break;
            }
        }
    }

    @Override
    public void stop() {
        io.println("Got stop signal for request worker task " + hashCode() + ".");
        stop = true;
    }

    @Override
    public boolean isReady() {
        return ready;
    }

    private void handleRequset(final Socket socket) {
        io.println("Opened connection from " + formatAddress(socket));

        try {
            new HandleRequestCommand(socket).execute();
        } catch (final Exception ex) {
            io.println("Error while talking with " + formatAddress(socket) + ": " + ex.getMessage());
        }
    }

    private static String formatAddress(final Socket socket) {
        return String.format("%s:%d", socket.getInetAddress(), socket.getPort());
    }
}

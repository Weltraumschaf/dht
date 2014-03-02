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
import de.weltraumschaf.dht.log.Log;
import de.weltraumschaf.dht.log.Logger;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class RequestWorker implements Task {


    /**
     * Logging facility.
     */
    private static final Logger LOG = Log.getLogger(RequestWorker.class);
    private final RequestHandler requestHandler = new RequestHandler();
    private final ConnectionQueue<AsynchronousSocketChannel> queue;
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

            if (stop) {
                if (queue.isEmpty()) {
                    io.println("Request worker task " + hashCode() + " stopped.");
                    ready = true;
                    break;
                } else {
                    io.println(String.format("Queue not empty yet (%d) ... ", queue.size()));
                }
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

    private void handleRequset(final AsynchronousSocketChannel client) {
        LOG.debug("Opened connection from " + formatAddress(client));

        try {
            requestHandler.execute(client);
        } catch (final Exception ex) {
            LOG.debug("Error while talking with client" + formatAddress(client) + ": " + ex.getMessage());
        }
    }

    private static String formatAddress(final AsynchronousSocketChannel client) {
        try {
            final InetSocketAddress address = (InetSocketAddress) client.getRemoteAddress();
            return String.format("%s:%d", address.getHostString(), address.getPort());
        } catch (final IOException ex) {
            return "unknown";
        }
    }
}

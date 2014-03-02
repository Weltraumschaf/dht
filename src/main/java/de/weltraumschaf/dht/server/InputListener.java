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
import java.io.IOError;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import org.apache.commons.lang3.Validate;

/**
 * Put all accepted client connections into the queue.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class InputListener implements Task {

    private static final int MAX_BACK_LOG = 100;
    /**
     * Queue shared w/ consumer thread.
     */
    private final ConnectionQueue<AsynchronousSocketChannel> queue;

    private final InetSocketAddress address;
    private final IO io;
    /**
     * True if {@link #exit()} was invoked.
     *
     * Volatile because is changes by others thread w/ invoking {@link #exit() }.
     */
    private volatile boolean stop;
    /**
     * Will be true if the thread has cleaned up.
     *
     * Volatile so that other threads can read it w/ {@link #isReady() }.
     */
    private volatile boolean ready;

    public InputListener(final ConnectionQueue<AsynchronousSocketChannel> queue, final InetSocketAddress address, final IO io) {
        super();
        this.queue = Validate.notNull(queue, "Parameter >queue< must not be null!");
        this.address = Validate.notNull(address, "Parameter >address< must not be null!");
        this.io = Validate.notNull(io, "Parameter >io< must not be null!");
    }

    /**
     * Runs infinite loop to collect incoming requests by invoking {@link ServerSocket#accept()} and put the client
     * sockets into the {@link #queue}.
     *
     * Stops the infinite loop immediately if {@link #stop} si true.
     */
    @Override
    public void run() {
        final AsynchronousServerSocketChannel server;

        try {
            server = AsynchronousServerSocketChannel.open().bind(address, MAX_BACK_LOG);
        } catch (final IOException ex) {
            throw new IOError(ex);
        }

        while (true) {
            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(final AsynchronousSocketChannel ch, final Void att) {
                    // accept the next connection
                    server.accept(null, this);
                    queue.put(ch);
                }

                @Override
                public void failed(final Throwable t, final Void att) {
                    io.println("Connection failed:  " + t.getMessage());
                }
            });

            if (stop) {
                break;
            }
        }

        try {
            server.close();
        } catch (final IOException ex) {
            throw new IOError(ex);
        } finally {
            ready = true;
        }

        io.println("Input listener task " + hashCode() + " stopped.");
    }

    /**
     * Signals thread to stop work and exit.
     */
    @Override
    public void stop() {
        io.println("Got stop signal for input listener task " + hashCode() + ".");
        stop = true;
    }

    /**
     * Signals that the thread has stopped working.
     *
     * @return true if infinite work loop has ended
     */
    @Override
    public boolean isReady() {
        return ready;
    }
}

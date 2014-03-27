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
import de.weltraumschaf.dht.msg.Message;
import de.weltraumschaf.dht.msg.MessageBox;
import de.weltraumschaf.dht.msg.MessageSerializer;
import de.weltraumschaf.dht.msg.MessageType;
import de.weltraumschaf.dht.msg.StreamIo;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import org.apache.commons.lang3.Validate;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

/**
 * Processes the incoming requests.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class RequestWorker implements Task {

    /**
     * Logging facility.
     */
    private static final Logger LOG = Log.getLogger(RequestWorker.class);
    private final MessageSerializer serializer = new MessageSerializer();
    /**
     * To obtain requests to process from.
     */
    private final ConnectionQueue<AsynchronousSocketChannel> queue;
    /**
     * Used for I/O.
     *
     * TODO Decouple from I/O by using events.
     */
    private final IO io;
    /**
     * Used to store incoming messages.
     */
    private final MessageBox inbox;
    /**
     * Indicates the worker to empty the queue and then stops.
     */
    private volatile boolean stop;
    /**
     * Signals that the worker as stopped.
     */
    private volatile boolean ready;

    public RequestWorker(final ConnectionQueue<AsynchronousSocketChannel> queue, final IO io, final MessageBox inbox) {
        super();
        this.queue = Validate.notNull(queue, "Parameter >queue< must not be null!");
        this.io = Validate.notNull(io, "Parameter >io< must not be null!");
        this.inbox = Validate.notNull(inbox, "Parameter >inbox< must not be null!");
    }

    @Override
    public void run() {
        while (true) {
            if (queue.isEmpty()) {
                try {
                    Thread.sleep(1); // Prevent 100 % CPU usage.
                } catch (final InterruptedException ex) {
                    LOG.error("Can't sleep request worker llop!", ex);
                }
            } else {
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

    /**
     * Process an incoming request.
     *
     * @param client must not be {@code null}
     */
    private void handleRequset(final AsynchronousSocketChannel client) {
        LOG.debug("Opened connection from " + formatAddress(client));

        try (
                final DataInputStream input = new DataInputStream(Channels.newInputStream(client));
                final PrintWriter output = new PrintWriter(Channels.newOutputStream(client), true);) {
            final Message incomming = serializer.deserialize(StreamIo.read(input));

            if (isProtocollMessage(incomming)) {
                handleProtocollMessage(incomming);
                return;
            }

            inbox.put(incomming);
            io.println("");
            io.println("Message received.");
            output.println("re: " + incomming.getBody());
            output.flush();
        } catch (final IOException ex) {
            LOG.debug("Error while talking with client" + formatAddress(client) + ": " + ex.getMessage());
        }
    }

    /**
     * Formats the remote address (host:port) of incoming request.
     *
     * @param client must not be {@code null}
     * @return never {@code null}
     */
    private static String formatAddress(final AsynchronousSocketChannel client) {
        try {
            final InetSocketAddress address = (InetSocketAddress) client.getRemoteAddress();
            return String.format("%s:%d", address.getHostString(), address.getPort());
        } catch (final IOException ex) {
            return "unknown";
        }
    }

    private boolean isProtocollMessage(final Message incomming) {
        try {
            MatcherAssert.assertThat(
                    incomming.getType(),
                    anyOf(
                            is(MessageType.FIND_NODE),
                            is(MessageType.FIND_VALUE),
                            is(MessageType.PING),
                            is(MessageType.STORE)));
            return true;
        } catch (final AssertionError err) {
            return false;
        }
    }

    private void handleProtocollMessage(final Message incomming) {
        LOG.debug("Handle protocoll message.");

        switch (incomming.getType()) {
            case FIND_NODE:
                LOG.debug("Handle FIND_NODE request.");
                break;
            case FIND_VALUE:
                LOG.debug("Handle FIND_VALUE request.");
                break;
            case PING:
                LOG.debug("Handle PING request.");
                break;
            case STORE:
                LOG.debug("Handle STORE request.");
                break;
            default:
                throw new UnsupportedOperationException(String.format("Can't handle request %s!", incomming));
        }
    }
}

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

import de.weltraumschaf.commons.application.IO;
import de.weltraumschaf.dht.msg.MessageBox;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.lang3.Validate;

/**
 * Consider using https://github.com/netty/netty/tree/master/example/src/main/java/io/netty/example/echo
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Server {

    private static final int TIME_FACTOR = 1000;
    private static final int MAX_WAIT_COUNT = 5;
    private static final int MAX_BACK_LOG = 100;
    private static final int THREAD_POOL_SIZE = 1;

    private final IO io;
    private final MessageBox inbox;
    private final ConnectionQueue<AsynchronousSocketChannel> queue = new ConnectionQueue<AsynchronousSocketChannel>();

    private ExecutorService workerService;
    private String host = null;
    private int port = -1;
    private Task worker;
    private AsynchronousServerSocketChannel server;

    private volatile int waitCount;

    private volatile State state = State.NOT_RUNNING;

    public Server(final IO io, final MessageBox inbox) {
        super();
        this.io = Validate.notNull(io, "Parameter >io< must not be null!");
        this.inbox = Validate.notNull(inbox, "Parameter >inbox< must not be null!");
    }

    public void setHost(final String host) {
        this.host = Validate.notEmpty(host, "Parameter >host< must not be null or empty!");
    }

    public void setPort(final int port) {
        Validate.isTrue(PortValidator.isValid(port),
            String.format("Parameter >port< must be in range %s! Given >%d<.", PortValidator.range(), port));
        this.port = port;
    }

    public void start() throws IOException {
        if (null == host) {
            throw new IllegalStateException("Host not set!");
        }

        if (-1 == port) {
            throw new IllegalStateException("Port not set!");
        }

        if (state != State.NOT_RUNNING) {
            throw new IllegalStateException("Server not in state NOT_RUNNING!");
        }

        state = State.STARTING;

        queue.clear();
        worker = new RequestWorker(queue, io, inbox);
        workerService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        workerService.execute(worker);

        server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(host, port), MAX_BACK_LOG);
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

        state = State.RUNNING;
    }

    public void stop() throws IOException, InterruptedException {
        if (state != State.RUNNING) {
            throw new IllegalStateException("Server is not in state RUNNING!");
        }

        state = State.STOPPING;
        server.close();
        workerService.shutdown();
        worker.stop();

        while (true) {
            if (worker.isReady()) {
                break;
            }

            if (waitCount == MAX_WAIT_COUNT) {
                io.println(String.format("Max. wait %d reached! Aborting ...", MAX_WAIT_COUNT));
                break;
            }

            final int wait = calculateTimeout(waitCount);
            io.println(String.format("Waiting for worker task to be ready (%ds) ...", wait / TIME_FACTOR));
            Thread.sleep(wait);
            ++waitCount;
        }

        worker = null;
        workerService = null;

        state = State.NOT_RUNNING;
    }

    public boolean isRunning() {
        return state == State.RUNNING;
    }

    public State getState() {
        return state;
    }

    static int calculateTimeout(final int round) {
        Validate.isTrue(round > -1, "Parameter >round< must be greater than -1!");
        return TIME_FACTOR * (int) Math.pow(2, round);
    }

    public int countQueue() {
        return queue.size();
    }

    public static enum State {

        NOT_RUNNING, RUNNING, STARTING, STOPPING;
    }
}

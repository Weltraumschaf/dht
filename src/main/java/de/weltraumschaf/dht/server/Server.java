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
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
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
    private static final int MAX_WAIT_COUNT = 3;
    private static final int MAX_PORT = 65535;

    private static final int THREAD_POOL_SIZE = 1;

    private final IO io;
    private final ConnectionQueue<AsynchronousSocketChannel> queue = new ConnectionQueue<AsynchronousSocketChannel>();

    private ExecutorService listenerService;
    private ExecutorService workerService;
    private String host = null;
    private int port = -1;
    private Task worker;
    private Task listener;

    private volatile int waitCount;

    private volatile State state = State.NOT_RUNNING;

    public Server(final IO io) {
        super();
        this.io = Validate.notNull(io, "Parameter >io< must not be null!");
    }

    public void setHost(final String host) {
        this.host = Validate.notEmpty(host, "Parameter >host< must not be null or empty!");
    }

    public void setPort(final int port) {
        Validate.isTrue(port > 0, "Parameter >port< must be greater that 0!");
        Validate.isTrue(port < MAX_PORT, "Parameter >port< must be less that " + MAX_PORT + "!");
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
        initListener(initWorker());
        state = State.RUNNING;
    }

    private ConnectionQueue initWorker() {
        queue.clear();
        worker = new RequestWorker(queue, io);
        workerService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        workerService.execute(worker);
        return queue;
    }

    private void initListener(final ConnectionQueue queue) throws IOException {
        listener = new InputListener(queue, new InetSocketAddress(host, port), io);
        listenerService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        listenerService.execute(listener);
    }

    public void stop() throws IOException, InterruptedException {
        if (state != State.RUNNING) {
            throw new IllegalStateException("Server is not in state RUNNING!");
        }

        state = State.STOPPING;
        listenerService.shutdown();
        listener.stop();
        workerService.shutdown();
        worker.stop();

        for (;;) {
            if (worker.isReady() && listener.isReady()) {
                break;
            }

            if (!worker.isReady()) {
                io.println("Worker not ready!");
            }
            if (!listener.isReady()) {
                io.println("Listener not ready!");
            }

            if (waitCount == MAX_WAIT_COUNT) {
                io.println(String.format("Max. wait %d reached! Aborting ...", MAX_WAIT_COUNT));
                break;
            }

            final int wait = calculateTimeout(waitCount);
            io.println(String.format("Waiting for listener and worker task to be ready (%ds) ...", wait / TIME_FACTOR));
            Thread.sleep(wait);
            ++waitCount;
        }

        derefListener();
        derefWorker();

        state = State.NOT_RUNNING;
    }

    private void derefWorker() {
        worker = null;
        workerService = null;
    }

    private void derefListener() throws IOException {
        listener = null;
        listenerService = null;
        queue.clear();
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

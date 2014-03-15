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

import de.weltraumschaf.commons.IO;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.dht.id.NodeId;
import de.weltraumschaf.dht.msg.MessageBox;
import de.weltraumschaf.dht.server.Server;
import org.apache.commons.lang3.Validate;

/**
 * Global object holder.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ApplicationContext {

    /**
     * Name of application.
     */
    public static final String NAME = "dht";
    /**
     * Used new line character.
     */
    public static final String NL = "\n";
    /**
     * The encoding used by this application for any I/O.
     */
    public static final String ENCODING = "utf-8";

    private  IO io;
    /**
     * Version of the application.
     */
    private Version version;
    private CliOptions options;
    private Server server;
    private MessageBox inbox;
    private MessageBox outbox;
    private NodeId nodeId;
    private Clock clock;

    public void setIo(final IO io) {
        this.io = Validate.notNull(io);
    }

    public void setVersion(final Version version) {
        this.version = Validate.notNull(version);
    }

    public void setOptions(final CliOptions options) {
        this.options = Validate.notNull(options);
    }

    public void setServer(final Server server) {
        this.server = Validate.notNull(server);
    }

    public void setInbox(final MessageBox inbox) {
        this.inbox = Validate.notNull(inbox);
    }

    public void setOutbox(final MessageBox outbox) {
        this.outbox = Validate.notNull(outbox);
    }

    public void setNodeId(final NodeId nodeId) {
        this.nodeId = Validate.notNull(nodeId);
    }

    public void setClock(final Clock clock) {
        this.clock = Validate.notNull(clock);
    }

    /**
     * Get the applications I/O streams.
     *
     * @return never {@code null}
     */
    public IO getIoStreams() {
        return io;
    }

    /**
     * Get the applications version.
     *
     * @return never {@code null}
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Get the applications command line options.
     *
     * @return never {@code null}
     */
    public CliOptions getOptions() {
        return options;
    }

    public Server getServer() {
        return server;
    }

    public MessageBox getInbox() {
        return inbox;
    }

    public MessageBox getOutbox() {
        return outbox;
    }

    public NodeId getNodeId() {
        return nodeId;
    }

    public Clock getClock() {
        return clock;
    }
}

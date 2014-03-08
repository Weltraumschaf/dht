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

package de.weltraumschaf.dht.msg;

import com.google.common.base.Objects;
import java.net.InetSocketAddress;
import org.apache.commons.lang3.Validate;
import org.msgpack.annotation.Message;

/**
 * Immutable message address.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
@Message
public final class MessageAddress {

    /**
     * Address host name.
     */
    private final String hostname;
    /**
     * Address port.
     */
    private final int port;

    /**
     * Necessary for message pack.
     */
    MessageAddress() {
        this("", 0);
    }

    /**
     * Dedicated constructor.
     *
     * @param hostname must not be {@code null} or empty
     * @param port must not be negative
     */
    public MessageAddress(final String hostname, final int port) {
        super();
        this.hostname = Validate.notEmpty(hostname, "Parameter >hostname< must not be null or empty!");
        Validate.isTrue(port > -1, "Parameter >port< must not be negative!");
        this.port = port;
    }

    /**
     * Convenience constructor.
     *
     * @param address must not be {@code null}
     */
    public MessageAddress(final InetSocketAddress address) {
        this(address.getHostString(), address.getPort());
    }

    /**
     * Get the host name.
     *
     * @return never {@code null}
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Get the address port.
     *
     * @return not negative
     */
    public int getPort() {
        return port;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hostname, port);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof MessageAddress)) {
            return false;
        }

        final MessageAddress other = (MessageAddress) obj;
        return Objects.equal(hostname, other.hostname) && Objects.equal(port, other.port);
    }

    @Override
    public String toString() {
        return String.format("%s:%d", hostname, port);
    }

    /**
     * Creates a new network socket address.
     *
     * @return never {@code null}, always new instance
     */
    public InetSocketAddress newInetSocketAddress() {
        return new InetSocketAddress(hostname, port);
    }

}

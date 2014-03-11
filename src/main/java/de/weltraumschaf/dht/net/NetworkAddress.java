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

package de.weltraumschaf.dht.net;

import com.google.common.base.Objects;
import java.net.InetSocketAddress;
import org.apache.commons.lang3.Validate;

/**
 * Immutable message address.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class NetworkAddress {

    /**
     * Address host name.
     */
    private String hostname;
    /**
     * Address port.
     */
    private int port;

    /**
     * For serialization.
     */
    public NetworkAddress() {
        this("localhost");
    }

    /**
     * Convenience constructor for address with no port.
     *
     * @param hostname must not be {@code null} or empty
     */
    public NetworkAddress(final String hostname) {
        this(hostname, 0);
    }

    /**
     * Dedicated constructor.
     *
     * @param hostname must not be {@code null} or empty
     * @param port must not be negative
     */
    public NetworkAddress(final String hostname, final int port) {
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
    public NetworkAddress(final InetSocketAddress address) {
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
        if (!(obj instanceof NetworkAddress)) {
            return false;
        }

        final NetworkAddress other = (NetworkAddress) obj;
        return Objects.equal(hostname, other.hostname) && Objects.equal(port, other.port);
    }

    @Override
    public String toString() {
        if (port == 0){
            return hostname;
        }

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

    public static NetworkAddress forValue(final String address) {
        final String trimmedAddress = Validate.notEmpty(address, "Parameter >address< must not be null or empty!");

        if (trimmedAddress.indexOf(":") > -1) {
            final String[] parts = trimmedAddress.split(":");
            Validate.isTrue(parts.length == 2,
                String.format("Bad address given >%s<! Use format >HOSTNAME:PORT<.", trimmedAddress));
            final String hostname = parts[0].trim();
            final String port = parts[1].trim();
            return new NetworkAddress(hostname, Integer.parseInt(port));
        }

        return new NetworkAddress(trimmedAddress);
    }

    public static NetworkAddress forValue(final InetSocketAddress address) {
        return new NetworkAddress(address.getHostString(), address.getPort());
    }

}

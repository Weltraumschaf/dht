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

import com.google.common.base.Objects;
import de.weltraumschaf.dht.id.DataKey;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.Validate;

/**
 * The data how a client is reachable.
 * <p>
 * This entity is immutable and will be stored in the DHT.
 * </p>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class ClientContact {

    /**
     * Global unique user id.
     */
    private final String userId;
    /**
     * Host on which the users client listen.
     */
    private final String host;
    /**
     * Port on which the users client listen.
     */
    private final int port;

    /**
     * Dedicated constructor.
     *
     * @param userId must not be {@code null} or empty
     * @param host must not be {@code null} or empty
     * @param port must be in range 0 .. 65535
     */
    public ClientContact(final String userId, final String host, final int port) {
        super();
        this.userId = Validate.notEmpty(userId, "Parameter >userId< must not be null or empty!");
        this.host = Validate.notEmpty(host, "Parameter >host< must not be null or empty!");
        Validate.isTrue(port > 0, "Parameter >port< must be greater than 0!");
        Validate.isTrue(port < 65535, "Parameter >port< must be less than 65535!");
        this.port = port;
    }

    public SocketAddress createAddress() {
        return new InetSocketAddress("cloudnine1999.no-ip.org", port);
    }

    public DataKey creteKey() {
        return new DataKey(DigestUtils.sha1(userId));
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("userId", userId).add("host", host).add("port", port).toString();
    }

}

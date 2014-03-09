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

import java.math.BigInteger;
import java.util.UUID;
import org.apache.commons.lang3.Validate;

/**
 * System wide unique node id.
 *
 * A node should generate a new node on each start up to join the network with.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class NodeId {

    private static final int LEFT_SHIFT = 0xFF;
    private final UUID id;

    public NodeId(final UUID id) {
        super();
        this.id = Validate.notNull(id, "Parameter >id< must not be null!");
    }

    public String asString() {
        return id.toString();
    }

    public BigInteger asInteger() {
        return BigInteger.valueOf(id.getMostSignificantBits())
            .shiftLeft(LEFT_SHIFT)
            .and(BigInteger.valueOf(id.getLeastSignificantBits()));
    }

    static BigInteger create128bitInteger(final long high, final long low) {
        return BigInteger.valueOf(high);
    }

    public static NodeId newRandom() {
        return new NodeId(UUID.randomUUID());
    }

}

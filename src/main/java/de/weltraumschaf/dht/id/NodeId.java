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
package de.weltraumschaf.dht.id;

import de.weltraumschaf.dht.data.KBucketKey;
import java.io.IOError;
import java.io.IOException;
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
public final class NodeId implements KBucketKey {

    public static final NodeId NULL = new NodeId(UUID.fromString("00000000-0000-0000-0000-000000000000"));

    /**
     * Holds the UUID.
     */
    private final UUID id;

    /**
     * Dedicated constructor.
     *
     * <p>
     * You should use {@link #newRandom()} to get an instance.
     * </p>
     *
     * @param id must not be {@code null}
     */
    NodeId(final UUID id) {
        super();
        this.id = Validate.notNull(id, "Parameter >id< must not be null!");
    }

    /**
     * Returns the standardized string format.
     *
     * <p>
     * Example: {@code f07bb8b7-cdf9-4731-8992-d538dc3abc0b}
     * </p>
     *
     * @return never {@code null} or empty
     */
    public String asString() {
        return id.toString();
    }

    /**
     * Returns the id as 128 bit integer.
     *
     * @return never {@code null}, always new instance
     */
    public BigInteger asInteger() {
        try {
            return UuidConverter.toBigInteger(id);
        } catch (final IOException ex) {
            throw new IOError(ex); // Should never hapen.
        }
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return id.equals(obj);
    }

    @Override
    public byte[] data() {
        try {
            return UuidConverter.toByteArray(id);
        } catch (final IOException ex) {
            // Should not happen: No file or network I/O here. Only streams to buffer bytes used.
            throw new IOError(ex);
        }
    }

    /**
     * Creates a new random id.
     *
     * @return never {@code null}, always new instance
     */
    public static NodeId newRandom() {
        return new NodeId(UUID.randomUUID());
    }

    public static NodeId valueOf(final String uuid) {
        return new NodeId(UUID.fromString(Validate.notEmpty(uuid, "Parameter >uuid< must not be null or empty!")));
    }
}

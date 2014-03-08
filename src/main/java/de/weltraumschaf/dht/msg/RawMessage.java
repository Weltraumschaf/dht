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
import java.util.Arrays;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.Validate;

/**
 * Immutable type of a raw message sent over the network.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class RawMessage {

    /**
     * Native type.
     *
     * One of {@link MessageType} values.
     */
    private final int type;
    /**
     * The raw data as bytes.
     */
    private final byte[] data;

    /**
     * Dedicated constructor.
     *
     * Makes defensive copy of input array.
     *
     * @param type one of {@link MessageType} values
     * @param data must not be {@code null}
     */
    public RawMessage(int type, byte[] data) {
        super();
        this.type = type;
        Validate.notNull(data, "Parameter >data< must not be null!");
        this.data = new byte[data.length];
        System.arraycopy(data, 0, this.data, 0, data.length);
    }

    /**
     * Get the message type.
     *
     * @return one of {@link MessageType} values
     */
    public int getType() {
        return type;
    }

    /**
     * Get the data bytes.
     *
     * @return defensive copy, never {@code null}
     */
    public byte[] getData() {
        final byte[] copy = new byte[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("type", type)
                .add("data", Hex.encodeHexString(data))
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, data);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof RawMessage)) {
            return false;
        }

        final RawMessage other = (RawMessage) obj;
        return Objects.equal(type, other.type) && Arrays.equals(data, other.data);
    }

}

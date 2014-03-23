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

import de.weltraumschaf.dht.net.NetworkAddress;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class ProtocollMessage<B> extends BaseMessage<B> {

    /**
     * Unique id so that response can be associated with requests.
     */
    private final int requestId;

    /**
     * Dedicated constructor.
     *
     * @param requestId must not be negative
     * @param from must not be {@code null}
     * @param to must not be {@code null}
     * @param body must not be {@code null}
     */
    public ProtocollMessage(final int requestId, final NetworkAddress from, final NetworkAddress to, final B body) {
        super(from, to, body);
        this.requestId = requestId;
    }

    @Override
    protected StringBuilder properties() {
        return super.properties().append(", ").append("requestId=").append(requestId);
    }

    /**
     * PING message.
     */
    final static class PingMessage<T> extends ProtocollMessage<T> {

        /**
         * For serialization.
         */
        public PingMessage() {
            this(-1, new NetworkAddress(), new NetworkAddress(), null);
        }

        /**
         * Dedicated constructor.
         *
         * @param requestId must not be negative
         * @param from must not be {@code null}
         * @param to must not be {@code null}
         * @param body must not be {@code null}
         */
        public PingMessage(final int requestId, final NetworkAddress from, final NetworkAddress to, T body) {
            super(requestId, from, to, body);
        }

        @Override
        public MessageType getType() {
            return MessageType.PING;
        }

    }

    /**
     * STORE message.
     */
    final static class StoreMessage<T> extends ProtocollMessage<T> {

        /**
         * For serialization.
         */
        public StoreMessage() {
            this(-1, new NetworkAddress(), new NetworkAddress(), null);
        }

        /**
         * Dedicated constructor.
         *
         * @param requestId must not be negative
         * @param from must not be {@code null}
         * @param to must not be {@code null}
         * @param body must not be {@code null}
         */
        public StoreMessage(final int requestId, final NetworkAddress from, final NetworkAddress to, T body) {
            super(requestId, from, to, body);
        }

        @Override
        public MessageType getType() {
            return MessageType.STORE;
        }

    }

    /**
     * FIND_NODE message.
     */
    final static class FindNodeMessage<T> extends ProtocollMessage<T> {

        /**
         * For serialization.
         */
        public FindNodeMessage() {
            this(-1, new NetworkAddress(), new NetworkAddress(), null);
        }

        /**
         * Dedicated constructor.
         *
         * @param requestId must not be negative
         * @param from must not be {@code null}
         * @param to must not be {@code null}
         * @param body must not be {@code null}
         */
        public FindNodeMessage(final int requestId, final NetworkAddress from, final NetworkAddress to, T body) {
            super(requestId, from, to, body);
        }

        @Override
        public MessageType getType() {
            return MessageType.FIND_NODE;
        }

    }

    /**
     * FIND_VALUE message.
     */
    final static class FindValueMessage<T> extends ProtocollMessage<T> {

        /**
         * For serialization.
         */
        public FindValueMessage() {
            this(-1, new NetworkAddress(), new NetworkAddress(), null);
        }

        /**
         * Dedicated constructor.
         *
         * @param requestId must not be negative
         * @param from must not be {@code null}
         * @param to must not be {@code null}
         * @param body must not be {@code null}
         */
        public FindValueMessage(final int requestId, final NetworkAddress from, final NetworkAddress to, T body) {
            super(requestId, from, to, body);
        }

        @Override
        public MessageType getType() {
            return MessageType.FIND_VALUE;
        }

    }
}

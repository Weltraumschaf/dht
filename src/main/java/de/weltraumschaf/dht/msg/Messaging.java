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

/**
 * Factory to get messaging implementations.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Messaging {

    /**
     * Factory to create protocol messages.
     */
    private static final ProcollMessages PROTOCOLL = new ProcollMessages();

    /**
     * Private for pure static factory.
     */
    private Messaging() {
        super();
        throw new UnsupportedOperationException("Don't call via reflection!");
    }

    /**
     * Create a new text message.
     *
     * @param from must not be {@code null}
     * @param to must not be {@code null}
     * @param body must not be {@code null}
     * @return never {@code null}, always new instance
     */
    public static Message newTextMessage(final NetworkAddress from, final NetworkAddress to, final String body) {
        return new TextMessage(from, to, body);
    }

    /**
     * Create a new protocol message.
     *
     * @param type type of message
     * @param from must not be {@code null}
     * @param to must not be {@code null}
     * @param body must not be {@code null}
     * @return never {@code null}, always new instance
     */
    public static Message newProtocollMessage(final MessageType type, final NetworkAddress from, final NetworkAddress to, final String body) {
        switch (type) {
            case PING:
                return PROTOCOLL.newPingMessage(from, to, body);
            case STORE:
                return PROTOCOLL.newStoreMessage(from, to, body);
            case FIND_NODE:
                return PROTOCOLL.newFindNodeMessage(from, to, body);
            case FIND_VALUE:
                return PROTOCOLL.newFindValueMessage(from, to, body);
            default:
                throw new IllegalArgumentException(String.format("Unsupported type >%s<!", type));
        }
    }

    /**
     * Create a new message box.
     *
     * @return never {@code null}, always new instance
     */
    public static MessageBox newMessageBox() {
        return new DefaultMessageBox();
    }

    /**
     * Creates a new sender.
     *
     * @return never {@code null}, always new instance
     */
    public static MessageSender newSender() {
        return new DefaultMessageSender();
    }

    /**
     * Factory to create protocol message with an unique id.
     */
    private static final class ProcollMessages {

        /**
         * Used to generate unique request id.
         */
        private int requestId = 0;

        /**
         * Get the next unique request id.
         *
         * @return not negative
         */
        private int nextId() {
            return ++requestId;
        }

        /**
         * Creates a new PING message.
         *
         * @param from must not be {@code null}
         * @param to must not be {@code null}
         * @param body must not be {@code null}
         * @return never {@code null}, always new instance
         */
        public ProtocollMessage newPingMessage(final NetworkAddress from, final NetworkAddress to, final String body) {
            return new ProtocollMessage(MessageType.PING, nextId(), from, to, body);
        }

        /**
         * Creates a new STORE message.
         *
         * @param from must not be {@code null}
         * @param to must not be {@code null}
         * @param body must not be {@code null}
         * @return never {@code null}, always new instance
         */
        public ProtocollMessage newStoreMessage(final NetworkAddress from, final NetworkAddress to, final String body) {
            return new ProtocollMessage(MessageType.STORE, nextId(), from, to, body);
        }

        /**
         * Creates a new FIND_NODE message.
         *
         * @param from must not be {@code null}
         * @param to must not be {@code null}
         * @param body must not be {@code null}
         * @return never {@code null}, always new instance
         */
        public  ProtocollMessage newFindNodeMessage(final NetworkAddress from, final NetworkAddress to, final String body) {
            return new ProtocollMessage(MessageType.FIND_NODE, nextId(), from, to, body);
        }

        /**
         * Creates a new FIND_VALUE message.
         *
         * @param from must not be {@code null}
         * @param to must not be {@code null}
         * @param body must not be {@code null}
         * @return never {@code null}, always new instance
         */
        public  ProtocollMessage newFindValueMessage(final NetworkAddress from, final NetworkAddress to, final String body) {
            return new ProtocollMessage(MessageType.FIND_VALUE, nextId(), from, to, body);
        }
    }
}

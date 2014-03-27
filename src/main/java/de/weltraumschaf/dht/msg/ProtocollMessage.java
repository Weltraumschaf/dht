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
 * Messages for the DHT protocol.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ProtocollMessage extends BaseMessage<String> {

    /**
     * Unique id so that response can be associated with requests.
     */
    private final int requestId;
    /**
     * Type of message.
     */
    private final MessageType type;

    /**
     * For serialization.
     */
    ProtocollMessage() {
        this(null, -1, new NetworkAddress(), new NetworkAddress(), null);
    }

    /**
     * Dedicated constructor.
     *
     * @param requestId must not be negative
     * @param from must not be {@code null}
     * @param to must not be {@code null}
     * @param body must not be {@code null}
     */
    public ProtocollMessage(final MessageType type, final int requestId, final NetworkAddress from, final NetworkAddress to, final String body) {
        super(from, to, body);
        this.type = type;
        this.requestId = requestId;
    }

    @Override
    protected StringBuilder properties() {
        return super.properties().append(", requestId=").append(requestId).append(", type=").append(type);
    }

    @Override
    public MessageType getType() {
        return type;
    }

}

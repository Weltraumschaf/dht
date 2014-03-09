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

import java.io.IOException;
import org.apache.commons.lang3.Validate;
import org.msgpack.MessagePack;

/**
 * (De-)Serialize messages.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class MessageSerianlizer {

    /**
     * Used to serialize the data.
     */
    private final MessagePack msgpack = new MessagePack();

    /**
     * Converts a message into a raw message.
     *
     * @param message must not be {@code null}
     * @return never {@code null}
     * @throws IOException on any serialization error
     */
    public RawMessage serialize(final Message message) throws IOException {
        Validate.notNull(message, "Parameter >message< must not be null!");
        return new RawMessage(message.getType().value(), msgpack.write(message));
    }

    /**
     * Converts a raw message back into a message.
     *
     * @param must not be {@code null}
     * @return never {@code null}
     * @throws IOException on any serialization error
     */
    public Message deserialize(final RawMessage raw) throws IOException {
        Validate.notNull(raw, "Parameter >raw< must not be null!");
        final MessageType type = MessageType.forValue(raw.getType());
        final Class<? extends Message> template;

        switch (type) {
            case TEXT:
                template = TextMessage.class;
                break;
            default:
                throw new IllegalArgumentException("Unsupported type " + type + "!");
        }

        return msgpack.read(raw.getData(), template);
    }

}
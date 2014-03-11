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
 * Implements a message type which has a plain text body.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class TextMessage extends BaseMessage<String>  {

    /**
     * For serialization.
     */
    public TextMessage() {
        this(new NetworkAddress(), new NetworkAddress(), "");
    }

    public TextMessage(final NetworkAddress from, final NetworkAddress to, final String body) {
        super(from, to, Validate.notNull(body, "Parameter >body< must not be null!"));
    }

    @Override
    public MessageType getType() {
        return MessageType.TEXT;
    }

}

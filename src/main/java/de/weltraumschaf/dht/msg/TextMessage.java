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
import java.net.InetSocketAddress;
import org.apache.commons.lang3.Validate;

/**
 * Implements a message type which hase a palin text body.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class TextMessage implements Message {

    /**
     * Holds the senders address.
     */
    private final InetSocketAddress from;
    /**
     * Holds the text message.
     */
    private final String body;
    /**
     * Flags if message is read or not.
     */
    private boolean unread = true;

    /**
     * Dedicated constructor.
     *
     * @param from must not be {@code null}
     * @param body must not be {@code null}
     */
    public TextMessage(final InetSocketAddress from, final String body) {
        super();
        this.from = Validate.notNull(from, "Parameter >from< must not be null!");
        this.body = Validate.notNull(body, "Parameter >body< must not be null!");
    }

    @Override
    public InetSocketAddress getFrom() {
        return from;
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void markAsRead() {
        synchronized (this) {
            unread = false;
        }
    }

    @Override
    public boolean isUnread() {
        synchronized (this) {
            return unread;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(from, body);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof TextMessage)) {
            return false;
        }

        final TextMessage other = (TextMessage) obj;
        return Objects.equal(getFrom(), other.getFrom()) && Objects.equal(getBody(), other.getBody());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("from", from).add("body", body).add("unread", unread).toString();
    }

}

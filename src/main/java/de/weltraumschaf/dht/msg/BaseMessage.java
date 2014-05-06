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

import de.weltraumschaf.commons.guava.Objects;
import de.weltraumschaf.dht.net.NetworkAddress;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseMessage<B> implements Message<B> {

    private final Object lock = new Object();
    /**
     * Holds the senders address.
     */
    private final NetworkAddress from;
    /**
     * Holds the receivers address.
     */
    private final NetworkAddress to;
    /**
     * Holds the text message.
     */
    private final B body;
    /**
     * Flags if message is read or not.
     */
    private boolean unread = true;

    /**
     * Dedicated constructor.
     *
     * @param from must not be {@code null}
     * @param to must not be {@code null}
     * @param body must not be {@code null}
     */
    public BaseMessage(final NetworkAddress from, final NetworkAddress to, final B body) {
        super();
        this.from = Validate.notNull(from, "Parameter >from< must not be null!");
        this.to = Validate.notNull(to, "Parameter >to< must not be null!");
        this.body = body;
    }

    @Override
    public NetworkAddress getFrom() {
        return from;
    }

    @Override
    public NetworkAddress getTo() {
        return to;
    }

    @Override
    public B getBody() {
        return body;
    }

    @Override
    public void markAsRead() {
        synchronized (lock) {
            unread = false;
        }
    }

    @Override
    public boolean isUnread() {
        synchronized (lock) {
            return unread;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(from, to, body);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof TextMessage)) {
            return false;
        }

        final TextMessage other = (TextMessage) obj;
        return Objects.equal(getFrom(), other.getFrom())
                && Objects.equal(getTo(), other.getTo())
                && Objects.equal(getBody(), other.getBody());
    }

    protected StringBuilder properties() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("from=").append(from).append(", ");
        buffer.append("to=").append(to).append(", ");
        buffer.append("body=").append(body).append(", ");
        buffer.append("unread=").append(unread);
        return buffer;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(getClass().getSimpleName()).append('{').append(properties()).append('}');
        return buffer.toString();
    }
}

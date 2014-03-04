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

import java.net.InetSocketAddress;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Messaging {

    public Messaging() {
        super();
    }

    public static Message newMessage(final InetSocketAddress from, final String body) {
        return new TextMessage(from, body);
    }

    public static MessageBox newMessageBox() {
        return new DefaultMessageBox();
    }

    private static class TextMessage implements Message {

        private final InetSocketAddress from;
        private final String body;
        private boolean unread = true;

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
            synchronized(this) {
                unread = true;
            }
        }

        @Override
        public boolean isUnread() {
            synchronized(this) {
                return unread;
            }
        }
    }

    private static class DefaultMessageBox implements MessageBox {

        public DefaultMessageBox() {
            super();
        }

        @Override
        public void put(Message msg) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void remove(Message msg) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int countUnread() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int count() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

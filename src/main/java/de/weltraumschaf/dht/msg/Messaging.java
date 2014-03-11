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
     * Private for pure static factory.
     */
    private Messaging() {
        super();
    }

    /**
     * Create a new message.
     *
     * @param from must not be {@code null}
     * @param to must not be {@code null}
     * @param body must not be {@code null}
     * @return never {@code null}, always new instance
     */
    public static Message newMessage(final NetworkAddress from, final NetworkAddress to, final String body) {
        return new TextMessage(from, to, body);
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
}

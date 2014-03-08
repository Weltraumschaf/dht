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

/**
 * Senders can send messages over the network.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface MessageSender {

    /**
     * Sends a message.
     *
     * @param message must not be {@code null}
     * @throws IOException on any network I/O error
     */
    void send(Message message) throws IOException;

}

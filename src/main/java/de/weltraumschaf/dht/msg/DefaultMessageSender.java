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

import de.weltraumschaf.dht.Application;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.apache.commons.lang3.Validate;

/**
 * Synchronous socket based implementation.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class DefaultMessageSender implements MessageSender {

    @Override
    public void send(final Message message) throws IOException {
        Validate.notNull(message, "Parameter >message< must not be null!");
        final InetSocketAddress to = message.getTo().newInetSocketAddress();

        try (
            final Socket client = new Socket(to.getHostString(), to.getPort());
            final DataOutputStream output = new DataOutputStream(client.getOutputStream());
        ) {
            final byte[] data = message.getBody().getBytes(Application.ENCODING);
            output.writeInt(data.length);
            output.write(data, 0, data.length);
            output.flush();
        }
    }

}

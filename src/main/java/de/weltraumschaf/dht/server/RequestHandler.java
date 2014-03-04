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
package de.weltraumschaf.dht.server;

import de.weltraumschaf.commons.IO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import org.apache.commons.lang3.Validate;

/**
 * Answers a client request.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class RequestHandler  {

    private final IO io;

    public RequestHandler(final IO io) {
        super();
        this.io = Validate.notNull(io, "Parameter >io< must not be null!");
    }


    public void execute(final AsynchronousSocketChannel client) throws IOException, InterruptedException {
        Validate.notNull(client, "Parameter >client< must not be null!");

        try (
            final BufferedReader input = new BufferedReader(new InputStreamReader(Channels.newInputStream(client)));
            final PrintWriter output = new PrintWriter(Channels.newOutputStream(client), true);
        ) {
            final String inputLine = input.readLine();
            io.println("");
            io.println(String.format("Received (%s): %s", RequestWorker.formatAddress(client), inputLine));
            output.println("re: " + inputLine);
        }
    }

}

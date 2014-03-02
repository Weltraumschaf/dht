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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Answers a client request.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class HandleRequestCommand implements Command<Object> {

    private final Socket socket;

    public HandleRequestCommand(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public void execute() throws Exception {
        final BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        final String inputLine = input.readLine();
//        final Command<String> cmd = CalculatePasswordCommand.createDefault(inputLine);
//        cmd.execute();
        final PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
//        output.println(cmd.getResult());
        output.println("hello");
//        Log.info("Close connection from " + socket.getInetAddress().toString());
        output.close();
        input.close();
        socket.close();
    }

    @Override
    public Object getResult() {
        return new Object();
    }

}

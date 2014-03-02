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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.commons.lang3.Validate;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Server {

    private static final int MAX_PORT = 65535;
    private String host = null;
    private int port = -1;
    private boolean running;

    public void setHost(final String host) {
        this.host = Validate.notEmpty(host, "Parameter >host< must not be null or empty!");
    }

    public void setPort(final int port) {
        Validate.isTrue(port > 0, "Parameter >port< must be greater that 0!");
        Validate.isTrue(port < MAX_PORT, "Parameter >port< must be less that " + MAX_PORT + "!");
        this.port = port;
    }

    public void start() {
        if (null == host) {
            throw new IllegalStateException("Host not set!");
        }

        if (-1 == port) {
            throw new IllegalStateException("Port not set!");
        }

        try (
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out
                = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));) {

            String inputLine, outputLine;

            // Initiate conversation with client
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                outputLine = kkp.processInput(inputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye.")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + port + " or listening for a connection");
            System.out.println(e.getMessage());
        }

        running = true;
    }

    public void stop() {
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    private static class KnockKnockProtocol {

        private static final int WAITING = 0;
        private static final int SENTKNOCKKNOCK = 1;
        private static final int SENTCLUE = 2;
        private static final int ANOTHER = 3;

        private static final int NUMJOKES = 5;

        private int state = WAITING;
        private int currentJoke = 0;

        private String[] clues = {"Turnip", "Little Old Lady", "Atch", "Who", "Who"};
        private String[] answers = {"Turnip the heat, it's cold in here!",
            "I didn't know you could yodel!",
            "Bless you!",
            "Is there an owl in here?",
            "Is there an echo in here?"};

        public String processInput(String theInput) {
            String theOutput = null;

            if (state == WAITING) {
                theOutput = "Knock! Knock!";
                state = SENTKNOCKKNOCK;
            } else if (state == SENTKNOCKKNOCK) {
                if (theInput.equalsIgnoreCase("Who's there?")) {
                    theOutput = clues[currentJoke];
                    state = SENTCLUE;
                } else {
                    theOutput = "You're supposed to say \"Who's there?\"! "
                            + "Try again. Knock! Knock!";
                }
            } else if (state == SENTCLUE) {
                if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
                    theOutput = answers[currentJoke] + " Want another? (y/n)";
                    state = ANOTHER;
                } else {
                    theOutput = "You're supposed to say \""
                            + clues[currentJoke]
                            + " who?\""
                            + "! Try again. Knock! Knock!";
                    state = SENTKNOCKKNOCK;
                }
            } else if (state == ANOTHER) {
                if (theInput.equalsIgnoreCase("y")) {
                    theOutput = "Knock! Knock!";
                    if (currentJoke == (NUMJOKES - 1)) {
                        currentJoke = 0;
                    } else {
                        currentJoke++;
                    }
                    state = SENTKNOCKKNOCK;
                } else {
                    theOutput = "Bye.";
                    state = WAITING;
                }
            }
            return theOutput;
        }
    }
}

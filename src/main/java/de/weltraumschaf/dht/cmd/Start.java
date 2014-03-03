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
package de.weltraumschaf.dht.cmd;

import de.weltraumschaf.dht.shell.CommandMainType;
import static de.weltraumschaf.dht.shell.CommandMainType.START;
import java.io.IOException;

/**
 * Executes `start` command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Start extends BaseCommand {

    @Override
    public void execute() {
        if (getApplication().getServer().isRunning()) {
            println(String.format("Server already listening on %s.", formatListenedAddress()));
            return;
        }

        println("Starting server ...");

        try {
            getApplication().getServer().start();
        } catch (final IOException ex) {
            println(String.format(
                    "Exception caught when trying to listen on %s or listening for a connection", formatListenedAddress()));

            if (isDebug()) {
                printStackTrace(ex);
            }
        }

        println(String.format("Server listening on %s.", formatListenedAddress()));
    }

    @Override
    public Descriptor getDescriptor() {
        return new BaseDescriptor() {

            @Override
            public CommandMainType getCommand() {
                return START;
            }

            @Override
            public String getUsage() {
                return "start";
            }

            @Override
            public String getHelpDescription() {
                return "Starts the server.";
            }
        };
    }
}

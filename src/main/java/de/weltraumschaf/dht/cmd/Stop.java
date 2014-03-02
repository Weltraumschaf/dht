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

import java.io.IOException;

/**
 * Executes `stop` command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Stop extends BaseCommand {

    @Override
    public void execute() {
        if (!getApplication().getServer().isRunning()) {
            println("Server not running.");
            return;
        }

        println("Stopping server ...");

        try {
            getApplication().getServer().stop();
        } catch (final IOException | InterruptedException ex) {
            println(String.format(
                    "Exception caught when trying to stop listen on %s.", formatListenedAddress()));

            if (isDebug()) {
                printStackTrace(ex);
            }
        }

        println(String.format("Stopped listening on %s:%d.",
                getApplication().getOptions().getHost(),
                getApplication().getOptions().getPort()));
    }
}

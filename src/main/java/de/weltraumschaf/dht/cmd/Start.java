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

import de.weltraumschaf.commons.shell.Token;
import de.weltraumschaf.dht.Application;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Start extends BaseCommand {

    /**
     * Dedicated constructor.
     *
     * @param app invoking application
     * @param arguments command arguments
     */
    public Start(final Application app, final List<Token> arguments) {
        super(app, arguments);
    }

    @Override
    public void execute() {
        if (getApplication().getServer().isRunning()) {
            getApplication().getIoStreams()
                    .println(String.format("Server already listening on %s.", formatListenedAddress()));
            return;
        }

        getApplication().getIoStreams().println("Starting server ...");

        try {
            getApplication().getServer().start();
        } catch (final IOException ex) {
            getApplication().getIoStreams()
                .println(String.format(
                    "Exception caught when trying to listen on %s or listening for a connection", formatListenedAddress()));

            if (getApplication().getOptions().isDebug()) {
                getApplication().getIoStreams().printStackTrace(ex);
            }
        }

        getApplication().getIoStreams()
                .println(String.format("Server listening on %s.", formatListenedAddress()));
    }

}

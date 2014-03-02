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
 * Executes `exit` command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Exit extends BaseCommand {

    /**
     * Dedicated constructor.
     *
     * @param app invoking application
     * @param arguments command arguments
     */
    public Exit(final Application app, final List<Token> arguments) {
        super(app, arguments);
    }

    @Override
    public void execute() {
        try {
            if (getApplication().getServer().isRunning()) {
                getApplication().getServer().stop();
            }
        } catch (final IOException | InterruptedException ex) {
            errorln("Error: " + ex.getMessage());

            if (isDebug()) {
                printStackTrace(ex);
            }
        }

        println("Bye bye & HAND!");
    }

}

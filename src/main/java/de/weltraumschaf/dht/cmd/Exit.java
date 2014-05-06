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

import de.weltraumschaf.dht.shell.MainCommand;
import static de.weltraumschaf.dht.shell.MainCommand.EXIT;
import java.io.IOException;

/**
 * Executes `exit` command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Exit extends BaseCommand {

    @Override
    public Descriptor getDescriptor() {
        return new BaseDescriptor() {

            @Override
            public MainCommand getCommand() {
                return EXIT;
            }

            @Override
            public String getUsage() {
                return EXIT.toString();
            }

            @Override
            public String getHelpDescription() {
                return "Exit the interactive shell.";
            }
        };
    }

    @Override
    public void execute() {
        try {
            if (getApplicationContext().getServer().isRunning()) {
                getApplicationContext().getServer().stop();
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

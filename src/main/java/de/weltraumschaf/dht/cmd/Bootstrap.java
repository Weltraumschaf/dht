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

import de.weltraumschaf.dht.CliOptions;
import de.weltraumschaf.dht.shell.CommandMainType;
import static de.weltraumschaf.dht.shell.CommandMainType.BOOTSTRAP;

/**
 * Executes `bootstrap` command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Bootstrap extends BaseCommand {

    @Override
    public Descriptor getDescriptor() {
        return new BaseDescriptor() {

            @Override
            public CommandMainType getCommand() {
                return BOOTSTRAP;
            }

            @Override
            public String getUsage() {
                return String.format("%s <host> [ <port> ]", BOOTSTRAP.toString());
            }

            @Override
            public String getHelpDescription() {
                return String.format(
                    "Bootstraps the network. If optional port is not given, then %d is used.",
                    CliOptions.DEFAULT_PORT);
            }
        };
    }

    @Override
    public void execute() {
        println("Start bootstrapping...");
        println("Bootstrapping done.");
    }

}

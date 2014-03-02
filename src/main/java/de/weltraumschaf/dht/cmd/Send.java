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
import java.util.List;

/**
 * Executes `send` command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Send extends BaseCommand {

    @Override
    public void execute() {
        List<Token> args = getArguments();
        println("  host:    " + args.get(0).toString());
        println("  port:    " + args.get(1).toString());
        println("  message: " + args.get(2).toString());
    }
}

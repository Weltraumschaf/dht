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
import de.weltraumschaf.commons.shell.TokenType;
import de.weltraumschaf.dht.shell.CommandArgumentExcpetion;
import java.util.List;

/**
 * Executes `send` command.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Send extends BaseCommand {

    @Override
    public void execute() {
        final List<Token> args = getArguments();

        final Token<String> hostToken = args.get(0);

        if (hostToken.getType() != TokenType.LITERAL) {
            throw new CommandArgumentExcpetion("Host must be a literal!");
        }

        final String host = hostToken.getValue();
        println("  host:    " + host);

        final Token<Integer> portToken = args.get(1);

        if (portToken.getType() != TokenType.NUMBER) {
            throw new CommandArgumentExcpetion("Port must be a number!");
        }

        final Integer port = portToken.getValue();
        println("  port:    " + port.toString());

        final Token<String> messageToken = args.get(2);

        if (messageToken.getType() != TokenType.STRING && messageToken.getType() != TokenType.LITERAL) {
            throw new CommandArgumentExcpetion("Message must be a string or literal!");
        }

        final String message = messageToken.getValue();
        println("  message: " + message);
    }
}

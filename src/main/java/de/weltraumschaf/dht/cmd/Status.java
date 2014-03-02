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
import java.util.List;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Status extends BaseCommand {

    /**
     * Dedicated constructor.
     *
     * @param app invoking application
     * @param arguments command arguments
     */
    public Status(final Application app, final List<Token> arguments) {
        super(app, arguments);
    }

    @Override
    public void execute() {
        final StringBuilder buffer = new StringBuilder();

        buffer.append("Server:").append(nl());
        buffer.append("  State:   ").append(getApplication().getServer().getState()).append(nl());
        buffer.append("  Address: ").append(formatListenedAddress()).append(nl());

        println(buffer.toString());
    }

}

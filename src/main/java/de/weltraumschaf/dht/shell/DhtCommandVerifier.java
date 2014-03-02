/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.dht.shell;

import de.weltraumschaf.commons.shell.CommandVerifier;
import de.weltraumschaf.commons.shell.ShellCommand;
import de.weltraumschaf.commons.shell.SyntaxException;

/**
 * Verifies the commands.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DhtCommandVerifier implements CommandVerifier {

    /**
     * Verifies parsed command of consistency.
     *
     * Consistency checks are: - correct sub command type - correct number of arguments
     *
     * @param cmd command to verify
     * @throws SyntaxException if, verification has failed
     */
    @Override
    public void verifyCommand(final ShellCommand cmd) throws SyntaxException {
        switch ((CommandMainType) cmd.getCommand()) {
            case EXIT:
            case HELP:
            case STATUS:
            case START:
            case STOP:
                if (cmd.getSubCommand() != CommandSubType.NONE) {
                    throw new SyntaxException(String.format("Command '%s' does not support subcommand '%s'!",
                            cmd.getCommand(), cmd.getSubCommand()));
                }

                if (!cmd.getArguments().isEmpty()) {
                    throw new SyntaxException(String.format("Command '%s' does not support arguments!",
                            cmd.getCommand()));
                }
                break;
            default:
            // Nothing to do here.
        }
    }

}

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
import de.weltraumschaf.commons.shell.Token;
import de.weltraumschaf.commons.shell.TokenType;
import java.util.List;

/**
 * Verifies the commands.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class NeuronCommandVerifier implements CommandVerifier {

    /**
     * Required number of arguments for message command.
     */
    private static final int MESSAGE_ARGS_COUNT = 3;

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
        switch ((NeuronMainType) cmd.getCommand()) {
            case EXIT:
            case HELP:
                if (cmd.getSubCommand() != NeuronSubType.NONE) {
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

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
import de.weltraumschaf.commons.shell.SubCommandType;
import de.weltraumschaf.commons.shell.SyntaxException;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

/**
 * Verifies the commands.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DhtCommandVerifier implements CommandVerifier {

    private static final int ARGS_1 = 1;
    private static final int ARGS_2 = 2;
    private static final int ARGS_3 = 3;

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
        switch ((CommandMainType) cmd.getMainCommand()) {
            // No argument, no subcommand.
            case EXIT:
            case STATUS:
            case START:
            case STOP:
                assertNoSubCommand(cmd);
                assertNoArguments(cmd);
                break;
            // Nor arguments w/ subcommand
            case HELP:
                assertNoArguments(cmd);
                break;
            // 1 or 2 arguments, w/o subcommand
            case BOOTSTRAP:
                assertTwoOrThreeArguments(cmd);
                break;
            // 3 arguments, w/o subcommand
            case SEND:
                assertNoSubCommand(cmd);
                assertArgumentsCount(cmd, ARGS_3);
                break;
            default:
            // Nothing to do here.
        }
    }

    private void assertNoArguments(final ShellCommand cmd) throws SyntaxException {
        try {
            MatcherAssert.assertThat(cmd.getArguments(), is(empty()));
        } catch (final AssertionError err) {
            throw new SyntaxException(
                String.format("Command '%s' does not support arguments!", cmd.getMainCommand()),
                err
            );
        }
    }

    private void assertArgumentsCount(final ShellCommand cmd, final int expectedCount) throws SyntaxException {
        try {
            MatcherAssert.assertThat(cmd.getArguments(), hasSize(expectedCount));
        } catch (final AssertionError err) {
            throw new SyntaxException(
                String.format("Command '%s' expects %d arguments!", cmd.getMainCommand(), expectedCount),
                err
            );
        }
    }

    private void assertNoSubCommand(final ShellCommand cmd) throws SyntaxException {
        try {
            MatcherAssert.assertThat(cmd.getSubCommand(), is(equalTo((SubCommandType) CommandSubType.NONE)));
        } catch (final AssertionError err) {
            throw new SyntaxException(
                String.format("Command '%s' does not support subcommand '%s'!", cmd.getMainCommand(), cmd.getSubCommand()),
                err
            );
        }
    }

    private void assertTwoOrThreeArguments(final ShellCommand cmd) throws SyntaxException {
        try {
            MatcherAssert.assertThat(cmd.getArguments(), either(hasSize(ARGS_2)).or(hasSize(ARGS_3)));
        } catch (final AssertionError err) {
            throw new SyntaxException(
                String.format("Command '%s' expects one or two arguments!", cmd.getMainCommand()),
                err
            );
        }
    }

}

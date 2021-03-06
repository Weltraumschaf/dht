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

import de.weltraumschaf.commons.shell.Parser;
import de.weltraumschaf.commons.shell.Parsers;
import de.weltraumschaf.commons.shell.ShellCommand;
import de.weltraumschaf.commons.shell.SyntaxException;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ParserTest {

    // CHECKSTYLE:OFF
    @Rule public ExpectedException thrown = ExpectedException.none();
    // CHECKSTYLE:ON
    private final Parser sut = Parsers.newParser(new DhtCommandVerifier(), new DhtLiteralCommandMap());

    @Test
    public void parse_comand() throws SyntaxException {
        ShellCommand c = sut.parse("help");
        assertThat((MainCommand) c.getMainCommand(), is(MainCommand.HELP));
        assertThat((SubCommand) c.getSubCommand(), is(SubCommand.NONE));
        assertThat(c.getArguments().size(), is(0));

        c = sut.parse("exit");
        assertThat((MainCommand) c.getMainCommand(), is(MainCommand.EXIT));
        assertThat((SubCommand) c.getSubCommand(), is(SubCommand.NONE));
        assertThat(c.getArguments().size(), is(0));
    }

    @Test
    public void parse_exceptionIfFirstTokenIsNotLiteral() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command expected as first input!");
        sut.parse("1234");
    }

    @Test
    public void parse_exceptionIfFirstLiteralIsNotACommand() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command expected as first input!");
        sut.parse("foobar");
    }

    @Test @Ignore
    public void parse_throwExceptionIfExitHasSubcommand() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'exit' does not support subcommand 'add'!");
        sut.parse("exit add");
    }

    @Test
    public void parse_throwExceptionIfExitHasArguments() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'exit' does not support arguments!");
        sut.parse("exit 123");
    }

    @Test
    @Ignore
    public void parse_throwExceptionIfHelpHasSubcommand() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'help' does not support subcommand 'add'!");
        sut.parse("help add");
    }

    @Test
    @Ignore
    public void parse_throwExceptionIfHelpHasMoreThanOneArguments() throws SyntaxException {
        thrown.expect(SyntaxException.class);
        thrown.expectMessage("Command 'help' expects maximum 1 arguments!");
        sut.parse("help foo bar");
    }

}

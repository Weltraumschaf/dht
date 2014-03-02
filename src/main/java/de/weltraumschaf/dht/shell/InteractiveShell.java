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
package de.weltraumschaf.dht.shell;

import com.google.common.collect.Lists;
import de.weltraumschaf.commons.IO;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.commons.shell.Parser;
import de.weltraumschaf.commons.shell.Parsers;
import de.weltraumschaf.commons.shell.ShellCommand;
import de.weltraumschaf.commons.shell.SubCommandType;
import de.weltraumschaf.commons.shell.SyntaxException;
import de.weltraumschaf.dht.Main;
import de.weltraumschaf.dht.cmd.Command;
import de.weltraumschaf.dht.cmd.CommandFactory;
import java.io.IOException;
import java.util.List;
import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;

/**
 * Implements a simple REPL shell.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class InteractiveShell {

    /**
     * String used as shell prompt.
     */
    private static final String PROMPT = Main.NAME + "> ";

    /**
     * ShellCommand line I/O.
     */
    private final IO io;

    /**
     * Shell user input parser.
     */
    private final Parser parser = Parsers.newParser(new NeuronCommandVerifier(), new NeuronLiteralCommandMap());

    /**
     * Factory to create commands.
     */
    private final CommandFactory factory;

    /**
     * Indicates if the REPL loop is running.
     */
    private boolean stop;

    /**
     * Default constructor.
     *
     * @param io injection point for I/O
     * @throws IOException if, version properties could not be loaded for {@link Version}
     */
    public InteractiveShell(final IO io) throws IOException {
        super();
        this.io = io;
        factory = new CommandFactory(this.io);
    }

    /**
     * Start the REPL.
     *
     * @throws IOException if I/O error occurs
     */
    public void start() throws IOException {
        io.println(String.format("Welcome to Neuro Interactive Shell!%n"));
        final ConsoleReader reader = new ConsoleReader(io.getStdin(), io.getStdout());
        reader.addCompleter(createCompletionHints());
        reader.setPrompt(PROMPT);

        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            try {
                final ShellCommand cmd = parser.parse(inputLine);
                execute(cmd);
            } catch (SyntaxException ex) {
                io.println("Error: " + ex.getMessage());
            }

            if (stop) {
                break;
            }
        }
    }

    /**
     * Stops the REPL.
     */
    public void exit() {
        stop = true;
    }

    /**
     * Executes a parsed command.
     *
     * @param shellCmd command to execute
     */
    private void execute(final ShellCommand shellCmd) {
        final Command cmd = factory.newCommand(shellCmd);
        cmd.execute();

        if (shellCmd.getCommand() == NeuronMainType.EXIT) {
            exit();
        }
    }

    /**
     * Creates command tab tab completion hints.
     *
     * @return an aggregated completer
     */
    private Completer createCompletionHints() {
        final List<Completer> completers = Lists.newArrayList();
        final List<String> commandsWithoutSubCommand = Lists.newArrayList();

        for (final NeuronMainType t : NeuronMainType.values()) {
            final List<NeuronSubType> subCommandTypes = t.subCommands();

            if (subCommandTypes.isEmpty()) {
                commandsWithoutSubCommand.add(t.toString());
            } else {
                final List<String> subCommands = Lists.newArrayList();
                for (final SubCommandType s : subCommandTypes) {
                    subCommands.add(s.toString());
                }
                completers.add(new ArgumentCompleter(
                    new StringsCompleter(t.toString()),
                    new StringsCompleter(subCommands)
                ));
            }
        }

        completers.add(new StringsCompleter(commandsWithoutSubCommand));
        return new AggregateCompleter(completers);
    }

}

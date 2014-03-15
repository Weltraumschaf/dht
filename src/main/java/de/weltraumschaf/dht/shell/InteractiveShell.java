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
import de.weltraumschaf.commons.shell.Parser;
import de.weltraumschaf.commons.shell.Parsers;
import de.weltraumschaf.commons.shell.ShellCommand;
import de.weltraumschaf.commons.shell.SubCommandType;
import de.weltraumschaf.commons.shell.SyntaxException;
import de.weltraumschaf.dht.Application;
import de.weltraumschaf.dht.ApplicationContext;
import de.weltraumschaf.dht.cmd.Command;
import de.weltraumschaf.dht.cmd.CommandFactory;
import de.weltraumschaf.dht.log.Log;
import de.weltraumschaf.dht.log.Logger;
import java.io.IOException;
import java.util.List;
import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
import org.apache.commons.lang3.Validate;

/**
 * Implements a simple REPL shell.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class InteractiveShell {

    /**
     * The welcome string showed very first to the user.
     */
    private static final String WELCOME_FORMAT = "    ‹—« Welcome to the %s interactive shell »—›%n";
    /**
     * Logging facility.
     */
    private static final Logger LOG = Log.getLogger(InteractiveShell.class);
    /**
     * String used as shell prompt.
     */
    private static final String PROMPT = ApplicationContext.NAME + "> ";

    /**
     * ShellCommand line I/O.
     */
    private final ApplicationContext context;

    /**
     * Shell user input parser.
     */
    private final Parser parser = Parsers.newParser(new DhtCommandVerifier(), new DhtLiteralCommandMap());

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
     * @param context the invoking application
     * @throws ClassNotFoundException if command classes can't be found
     */
    public InteractiveShell(final ApplicationContext context) throws ClassNotFoundException {
        super();
        this.context = Validate.notNull(context, "Parameter >app< must not be null!");
        factory = new CommandFactory(this.context);
    }

    /**
     * Start the REPL.
     *
     * @throws IOException if I/O error occurs
     */
    public void start() throws IOException {
        showWelcome();

        final ConsoleReader reader = new ConsoleReader(context.getIoStreams().getStdin(), context.getIoStreams().getStdout());
        reader.addCompleter(createCompletionHints());
        reader.setPrompt(PROMPT);

        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
            try {
                if (inputLine.trim().isEmpty()) {
                    // Ignore empty input.
                    continue;
                }

                final ShellCommand cmd = parser.parse(inputLine);
                execute(cmd);
            } catch (final SyntaxException ex) {
                context.getIoStreams().println(ex.getMessage());
                context.getIoStreams().println("Try the command `help` to get description for all commands.");

                if (context.getOptions().isDebug()) {
                    context.getIoStreams().printStackTrace(ex);
                }
            } catch (final IllegalArgumentException ex) {
                context.getIoStreams().println(ex.getMessage());
            } catch (final CommandRuntimeException ex) {
                handleException("Error: ", ex);
            } catch (final InstantiationException | IllegalAccessException ex) {
                handleException("Fatal: ", ex);
            }

            if (stop) {
                break;
            }
        }
    }

    private void showWelcome() {
        context.getIoStreams().println(String.format(WELCOME_FORMAT, ApplicationContext.NAME.toUpperCase()));
        context.getIoStreams().println(String.format("Your node id is: %s%n", context.getNodeId().asString()));
    }

    private void handleException(final String prefix, final Exception ex) {
        context.getIoStreams().println(prefix + ex.getMessage());

        if (context.getOptions().isDebug()) {
            context.getIoStreams().printStackTrace(ex);
        }

        LOG.error(ex.getMessage(), ex);
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
    private void execute(final ShellCommand shellCmd) throws InstantiationException, IllegalAccessException {
        final Command cmd = factory.newCommand(shellCmd);
        cmd.execute();

        if (shellCmd.getCommand() == CommandMainType.EXIT) {
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

        for (final CommandMainType t : CommandMainType.values()) {
            final List<CommandSubType> subCommandTypes = t.subCommands();

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

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

import com.beust.jcommander.internal.Maps;
import de.weltraumschaf.commons.shell.MainCommandType;
import de.weltraumschaf.commons.shell.ShellCommand;
import de.weltraumschaf.dht.Application;
import de.weltraumschaf.dht.shell.CommandMainType;
import static de.weltraumschaf.dht.shell.CommandMainType.EXIT;
import static de.weltraumschaf.dht.shell.CommandMainType.HELP;
import java.util.Collections;
import java.util.Map;
import org.apache.commons.lang3.Validate;

/**
 * Factory to create command objects depending on the parsed {@link ShellCommand}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CommandFactory {

    /**
     * Invoking application.
     */
    private final Application app;

    private final Map<MainCommandType, Class<?>> classLookup;

    /**
     * Dedicated constructor.
     *
     * @param app the invoking application
     */
    public CommandFactory(final Application app) throws ClassNotFoundException {
        super();
        this.app = Validate.notNull(app, "Parameter >app< must not be null!");

        final Map<MainCommandType, Class<?>> temp = Maps.newHashMap();

        for (final CommandMainType type : CommandMainType.values()) {
            temp.put(type, Class.forName(generateClassName(type)));
        }

        classLookup = Collections.unmodifiableMap(temp);
    }

    private static String generateClassName(final CommandMainType type) {
        final String name = type.name().toLowerCase();
        return "de.weltraumschaf.dht.cmd." + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    /**
     * Create command instances according to the parsed shell command.
     *
     * @param shellCmd used to determine appropriate command
     * @return command object // CHECKSTYLE:OFF
     * @throws IllegalArgumentException if, can't create command of bad main or sub command type // CHECKSTYLE:ON
     */
    public Command newCommand(final ShellCommand shellCmd) throws InstantiationException, IllegalAccessException {
        if (classLookup.containsKey(shellCmd.getCommand())) {
            final Class<?> comamndClass = classLookup.get(shellCmd.getCommand());
            final Command cmd = (Command) comamndClass.newInstance();
            cmd.setApp(app);
            cmd.setArguments(shellCmd.getArguments());
            return cmd;
        }

        throw new IllegalArgumentException(String.format("Unsupported main command type '%s'!", shellCmd.getCommand()));
    }



}

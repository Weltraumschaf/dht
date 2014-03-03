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
import com.google.common.collect.Lists;
import de.weltraumschaf.commons.shell.MainCommandType;
import de.weltraumschaf.commons.shell.ShellCommand;
import de.weltraumschaf.dht.Application;
import de.weltraumschaf.dht.shell.CommandMainType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.Validate;

/**
 * Factory to create command objects depending on the parsed {@link ShellCommand}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class CommandFactory {

    /**
     * In which package the commands are.
     */
    private static final String COMMAND_PACKAGE = "de.weltraumschaf.dht.cmd";
    /**
     * Holds descriptors for all command classes.
     *
     * Lazy computed once speed up subsequent requests.
     */
    private static Collection<Descriptor> DESCRIPTORS = null;
    /**
     * Invoking application.
     */
    private final Application app;
    /**
     * Holds a map of command type and command classes.
     */
    private final Map<MainCommandType, Class<?>> classLookup;

    /**
     * Dedicated constructor.
     *
     * @param app the invoking application
     * @throws ClassNotFoundException if a command class is not found
     */
    public CommandFactory(final Application app) throws ClassNotFoundException {
        super();
        this.app = Validate.notNull(app, "Parameter >app< must not be null!");
        classLookup = createClassLookup();
    }

    /**
     * Generates class name for command type.
     *
     * @param type must not be {@code null}
     * @return never {@code null} or empty
     */
    private static String generateClassName(final CommandMainType type) {
        final String name = Validate.notNull(type).name().toLowerCase();
        return COMMAND_PACKAGE + "." + Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    /**
     * Creates the command type class lookup.
     *
     * Must never be more visible than private, unless remaining not final because used in constructor.
     *
     * @return never {@code null} or empty, unmodifiable map
     * @throws ClassNotFoundException if a command class is not found
     */
    private static Map<MainCommandType, Class<?>> createClassLookup() throws ClassNotFoundException {
        final Map<MainCommandType, Class<?>> temp = Maps.newHashMap();

        for (final CommandMainType type : CommandMainType.values()) {
            temp.put(type, Class.forName(generateClassName(type)));
        }

        return Collections.unmodifiableMap(temp);
    }

    /**
     * Get sorted list of all command descriptors.
     *
     * Sorting is done by the literal command.
     *
     * @return never {@code null}, unmodifiable
     * @throws ClassNotFoundException if a command class is not found
     * @throws InstantiationException if command can't be instantiated
     * @throws IllegalAccessException if default constructor is not accessible
     */
    static Collection<Descriptor> getDescriptors() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (null == DESCRIPTORS) {
            final List<Descriptor> descriptors = Lists.newArrayList();

            for (final Class<?> comamndClass : createClassLookup().values()) {
                descriptors.add(((Command) comamndClass.newInstance()).getDescriptor());
            }

            Collections.sort(descriptors);
            DESCRIPTORS = Collections.unmodifiableList(descriptors);
        }

        return DESCRIPTORS;
    }

    /**
     * Create command instances according to the parsed shell command.
     *
     * @param shellCmd used to determine appropriate command
     * @return command object
     * @throws InstantiationException if command can't be instantiated
     * @throws IllegalAccessException if default constructor is not accessible
     * CHECKSTYLE:OFF
     * @throws IllegalArgumentException if, can't create command of bad main or sub command type
     * CHECKSTYLE:ON
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

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

import de.weltraumschaf.dht.Application;
import de.weltraumschaf.dht.shell.CommandMainType;
import static de.weltraumschaf.dht.shell.CommandMainType.HELP;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Executes `help` command.
 *
 * @author @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
final class Help extends BaseCommand {

    /**
     * Usage strings (left column) width.
     */
    private static final int USAGE_WIDTH = 34;
    /**
     * Max width of help output.
     */
    private static final int MAX_WIDTH = 80;
    /**
     * Description strings (right column) width.
     */
    private static final int DESCRIPTION_WIDTH = MAX_WIDTH - USAGE_WIDTH;
    /**
     * Used new line character.
     */
    private static final String NL = Application.NL;
    /**
     * First line of help.
     */
    private static final String HEADLINE_FORMAT = "This is the %s interactive shell version %s.";

    @Override
    public void execute() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(String.format(HEADLINE_FORMAT, Application.NAME, getApplication().getVersion()))
              .append(NL).append(NL);
        buffer.append("Available commands:").append(NL).append(NL);

        try {
            for (final Descriptor descriptor : CommandFactory.getDescriptors()) {
                buffer.append(format(descriptor)).append(NL);
            }
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

        print(buffer.toString());
    }

    @Override
    public Descriptor getDescriptor() {
        return new BaseDescriptor() {

            @Override
            public CommandMainType getCommand() {
                return HELP;
            }

            @Override
            public String getUsage() {
                return "help";
            }

            @Override
            public String getHelpDescription() {
                return "Show all available commands.";
            }
        };
    }

    /**
     * Prefix usage with two spaces and pad right side up to {@link #USAGE_WIDTH}.
     *
     * @param usage must not be {@code null} or empty
     * @return never {@code nul} or empty
     */
    static String padUsage(final String usage) {
        return StringUtils.rightPad("  " + Validate.notEmpty(usage), USAGE_WIDTH);
    }

    /**
     * Wraps given description so it fits in a "column" with {@link #DESCRIPTION_WIDTH width}.
     *
     * @param usage must not be {@code null} or empty
     * @return never {@code nul} or empty
     */
    static String wrap(final String description) {
        return WordUtils.wrap(Validate.notEmpty(description), DESCRIPTION_WIDTH, NL, true);
    }

    /**
     * Formats whole descriptor.
     *
     * @param descriptor must not be {@code null}
     * @return never {@code null}
     */
    static String format(final Descriptor descriptor) {
        Validate.notNull(descriptor, "Parameter >descriptor< must not be null!");
        final StringBuilder buffer = new StringBuilder(padUsage(descriptor.getUsage()));
        boolean isFirstLine = true;

        for (final String line : StringUtils.split(wrap(descriptor.getHelpDescription()), NL)) {
            if (isFirstLine) {
                buffer.append(line);
                isFirstLine = false;
            } else {
                buffer.append(StringUtils.repeat(" ", USAGE_WIDTH)).append(line);
            }

            buffer.append(NL);
        }

        return buffer.toString();
    }
}

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

import de.weltraumschaf.dht.shell.CommandMainType;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link Help}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class HelpTest {

    @Test
    public void padUsage() {
        assertThat(Help.padUsage("help"), is("  help                                  "));
        assertThat(Help.padUsage("send <host> <port> <message>"), is("  send <host> <port> <message>          "));
    }

    @Test
    public void wrap() {
        assertThat(Help.wrap("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor "
                + "invidunt ut labore et dolore magna aliquyam"),
                is("Lorem ipsum dolor sit amet, consetetur\n"
                        + "sadipscing elitr, sed diam nonumy eirmod\n"
                        + "tempor invidunt ut labore et dolore\n"
                        + "magna aliquyam"));
        assertThat(Help.wrap("Sends <message> to <host:port>. The message must be encapsulated in quotes if it"
                + "has more than one word. Port must be in range of %s."),
                is("Sends <message> to <host:port>. The\n"
                        + "message must be encapsulated in quotes\n"
                        + "if ithas more than one word. Port must\n"
                        + "be in range of %s."));
    }

    @Test
    public void format() {
        assertThat(Help.format(new Descriptor() {

            @Override
            public String getUsage() {
                return "send <host> <port> <message>";
            }

            @Override
            public String getHelpDescription() {
                return String.format("Sends <message> to <host:port>. The message must be encapsulated in quotes if it"
                        + "has more than one word. Port must be in range of 1..3.");
            }

            @Override
            public CommandMainType getCommand() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public int compareTo(Descriptor o) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }), is("  send <host> <port> <message>          Sends <message> to <host:port>. The\n"
                + "                                        message must be encapsulated in quotes\n"
                + "                                        if ithas more than one word. Port must\n"
                + "                                        be in range of 1..3.\n"));
    }
}

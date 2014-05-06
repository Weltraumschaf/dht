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

import de.weltraumschaf.commons.shell.LiteralCommandMap;
import de.weltraumschaf.commons.shell.MainCommandType;
import de.weltraumschaf.commons.shell.SubCommandType;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DhtLiteralCommandMapTest {

    private static enum BadMainType implements MainCommandType {
        FOO("foo"), BAR("bar"), BAZ("baz");

        /**
         * Literal command string used in shell.
         */
        private final String literal;

        /**
         * Initialize name.
         *
         * @param name literal shell command string
         */
        private BadMainType(final String name) {
            this.literal = name;
        }

        @Override
        public String toString() {
            return literal;
        }
    }

    private static enum BadSubType implements SubCommandType {
        FOO("foo"), BAR("bar"), BAZ("baz");

        /**
         * Literal command string used in shell.
         */
        private final String literal;

        /**
         * Initialize name.
         *
         * @param name literal shell command string
         */
        private BadSubType(final String name) {
            this.literal = name;
        }

        @Override
        public String toString() {
            return literal;
        }
    }

    private final LiteralCommandMap sut = new DhtLiteralCommandMap();

    @Test
    public void isCommand() {
        assertThat(sut.isCommand(MainCommand.EXIT.toString()), is(true));
        assertThat(sut.isCommand(MainCommand.HELP.toString()), is(true));
        assertThat(sut.isCommand(BadMainType.FOO.toString()), is(false));
        assertThat(sut.isCommand(BadMainType.BAR.toString()), is(false));
        assertThat(sut.isCommand(BadMainType.BAZ.toString()), is(false));
    }

    @Test
    public void isSubCommand() {
        assertThat(sut.isSubCommand(SubCommand.HELP_EXIT.toString()), is(true));
        assertThat(sut.isSubCommand(SubCommand.HELP_SEND.toString()), is(true));
        assertThat(sut.isSubCommand(SubCommand.HELP_START.toString()), is(true));
        assertThat(sut.isSubCommand(SubCommand.HELP_STATUS.toString()), is(true));
        assertThat(sut.isSubCommand(SubCommand.HELP_STOP.toString()), is(true));
        assertThat(sut.isSubCommand(SubCommand.NONE.toString()), is(false));
        assertThat(sut.isSubCommand(BadSubType.FOO.toString()), is(false));
        assertThat(sut.isSubCommand(BadSubType.BAR.toString()), is(false));
        assertThat(sut.isSubCommand(BadSubType.BAZ.toString()), is(false));
    }

}

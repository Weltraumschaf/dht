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
import java.util.Map;

/**
 * Maps literal strings to keywords.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class DhtLiteralCommandMap extends LiteralCommandMap {

    /**
     * Default constructor.
     */
    public DhtLiteralCommandMap() {
        super(SubCommand.NONE);
    }

    @Override
    protected Class<MainCommand> getMainCommandType() {
        return MainCommand.class;
    }

    @Override
    protected Class<SubCommand> getSubCommandType() {
        return SubCommand.class;
    }

}

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
        super(CommandSubType.NONE);
    }

    @Override
    protected void initMainCommandMap(final Map<String, MainCommandType> map) {
        for (final CommandMainType t : CommandMainType.values()) {
            map.put(t.toString(), t);
        }
    }

    @Override
    protected void initSubCommandMap(final Map<String, SubCommandType> map) {
        for (final CommandSubType t : CommandSubType.values()) {
            if (t.toString().isEmpty()) {
                continue; // Ignore to do not recognize empty strings as sub command.
            }
            map.put(t.toString(), t);
        }
    }

}

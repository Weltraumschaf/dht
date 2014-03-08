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
package de.weltraumschaf.dht.msg;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum MessageType {

    TEXT(1);

    private static final Map<Integer, MessageType> LOOKUP = Maps.newHashMap();
    static {
        for (final MessageType type : values()) {
            LOOKUP.put(type.value(), type);
        }
    }

    private final int value;

    private MessageType(final int type) {
        this.value = type;
    }

    public int value() {
        return value;
    }

    public static MessageType forValue(final int value) {
        if (!LOOKUP.containsKey(value)) {
            throw new IllegalArgumentException("Does not have type for value " + value + "!");
        }

        return LOOKUP.get(value);
    }
}

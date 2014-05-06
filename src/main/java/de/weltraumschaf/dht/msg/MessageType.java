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

import de.weltraumschaf.commons.guava.Lists;
import de.weltraumschaf.commons.guava.Maps;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.Validate;

/**
 * The types of messages.
 *
 * A separated integer value is stored because over Java compilers it is not guaranteed that an enumeration has always
 * the same ordinal value.But we need a reliable native value to serialize over network.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum MessageType {

    /**
     * To send arbitrary ASCII text.
     */
    TEXT(0),
    /**
     * Used to verify that a node is still alive.
     */
    PING(1),
    /**
     * Stores a (key value) pair in one node.
     */
    STORE(2),
    /**
     * The recipient of the request will return the k nodes in his own buckets that are the closest ones to the
     * requested key.
     */
    FIND_NODE(3),
    /**
     * Same as {@link #FIND_NODE}, but if the recipient of the request has the requested key in its store, it will
     * return the corresponding value.
     */
    FIND_VALUE(4);

    /**
     * Maps a static integer representation to its enum object.
     */
    private static final Map<Integer, MessageType> LOOKUP = Maps.newHashMap();
    /**
     * Used to track already used values to prevent duplicates.
     */
    private static final List<Integer> USED_VLAUES = Lists.newArrayList();

    static {
        for (final MessageType type : values()) {
            if (USED_VLAUES.contains(type.value())) {
                throw new RuntimeException(String.format("Duplicate value >%d<!", type.value()));
            }

            LOOKUP.put(type.value(), type);
            USED_VLAUES.add(type.value());
        }
    }

    /**
     * Integer representation.
     */
    private final int value;

    /**
     * Dedicated constructor.
     *
     * @param type
     */
    private MessageType(final int type) {
        Validate.isTrue(type > -1, "Parameter >type< must not be negative!");
        this.value = type;
    }

    /**
     * Get the guaranteed integer representation.
     *
     * This value will not change in contrast to {@link #ordinal()}.
     *
     * @return not negative
     */
    public int value() {
        return value;
    }

    /**
     * Get enumeration corresponding to a given integer value.
     *
     * @param value must not be negative
     * @return never {@code null}
     */
    public static MessageType forValue(final int value) {
        if (!LOOKUP.containsKey(value)) {
            throw new IllegalArgumentException("Does not have type for value " + value + "!");
        }

        return LOOKUP.get(value);
    }

}

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
package de.weltraumschaf.dht.log;

/**
 * Tacoli is a nice easy to parse (with CLI tools) log format based on tabs and colons.
 *
 * See http://blog.jgc.org/2012/04/tacoli-simple-logging-format.html for more information.
 *
 * In this package a Tacoli layout for Log4J and a Tacoli message which provides key value
 * pairs are contained. The main difference is that this implementation uses the equal sign
 * instead of the colon.
 *
 * This interface declares the characters used to separate parts of the log.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Tacoli {

    /**
     * Separates key and value in log message.
     */
    char KEY_VALUE_DELIMITER = '=';
    /**
     * Separates log entries (lines).
     */
    char NEW_LINE = '\n';
    /**
     * Separates the parts of a log message (time, level, key value pairs).
     */
    char COLUMN_DELIMITER = '\t';

}

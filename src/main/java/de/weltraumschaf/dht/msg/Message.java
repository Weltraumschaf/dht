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

import java.net.InetSocketAddress;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Message {

    InetSocketAddress getFrom();

    String getBody();

    void markAsRead();

    boolean isUnread();

}

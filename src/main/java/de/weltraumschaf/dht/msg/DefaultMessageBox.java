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

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 * Implements a default message box to hold some messages.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class DefaultMessageBox implements MessageBox {

    private final List<Message> messages = Lists.newArrayList();
    private final Object lock = new Object();

    @Override
    public void put(final Message msg) {
        Validate.notNull(msg, "Parameter >msg< must not be null!");
        synchronized (lock) {
            messages.add(msg);
        }
    }

    @Override
    public void remove(final Message msg) {
        Validate.notNull(msg, "Parameter >msg< must not be null!");
        synchronized (lock) {
            messages.remove(msg);
        }
    }

    @Override
    public int countUnread() {
        int count = 0;
        // Work on copy to prevent concurrent modification exceptions.
        final Collection<Message> localCopy;
        synchronized (lock) {
            localCopy = Collections.unmodifiableCollection(messages);
        }

        for (final Message m : localCopy) {
            if (m.isUnread()) {
                ++count;
            }
        }

        return count;
    }

    @Override
    public int count() {
        synchronized (lock) {
            return messages.size();
        }
    }

    @Override
    public Collection<Message> getAll() {
        synchronized (lock) {
            return Collections.unmodifiableCollection(messages);
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (lock) {
            return messages.isEmpty();
        }
    }

}

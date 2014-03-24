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

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.DailyRollingFileAppender;

/**
 * Prepends the PID to the log file name.
 * <p>
 * In detail the PID or the {@link RuntimeMXBean#getName() name} followed by dash (-) is
 * prepended to the file name. This is used to have separate logs if you run your application
 * as multiple instances on your machine in multiple JVM processes. One use case is that you
 * develop a multi node system you run it multiple times from your repo on the same machine on
 * different ports e.g.
 * </p>
 *
 * Idea is from http://www.theresearchkitchen.com/archives/100
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class PidPrefixDailyRollingFileAppender extends DailyRollingFileAppender {

    /**
     * Indicates that PID was not found in name.
     */
    private static final int NOT_FOUND = -1;

    @Override
    public void setFile(final String file) {
        final RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        final int pid = findPid(runtime.getName());
        final StringBuilder finalName = new StringBuilder();

        if (NOT_FOUND == pid) {
            finalName.append(runtime.getName());
        } else {
            finalName.append(pid);
        }

        finalName.append('-').append(file);
        super.setFile(finalName.toString());
    }

    /**
     * Extracts the PID from a {@link RuntimeMXBean#getName() name}.
     * <p>
     * Typically an Unix/Linux the name looks like pid@hostname. This method try
     * to extract the pid part. If it can't extract it {@link #NOT_FOUND} will be returned.
     * </p>
     *
     * @param name must not be {@code null}
     * @return the not negative PID, or -1 for not found
     */
    static int findPid(final String name) {
        final int pos = Validate.notNull(name).indexOf('@');

        if (NOT_FOUND == pos) {
            return NOT_FOUND;
        }

        try {
        return Integer.parseInt(name.substring(0, pos));
        } catch (final NumberFormatException ex) {
            return NOT_FOUND;
        }
    }

}

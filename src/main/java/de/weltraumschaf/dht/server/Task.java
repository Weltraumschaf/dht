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

package de.weltraumschaf.dht.server;

/**
 * Task executable by {@link java.util.concurrent.ExecutorService}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
interface Task extends Runnable {

    /**
     * Tells task to stop.
     */
    void stop();
    /**
     * Returns whether the task is ready after {@link #stop()} was called.
     * <p>
     * If {@link #stop()} never was called the task will never be ready.
     * </p>
     *
     * @return {@code true} if task has finished work, else {@code false}
     */
    boolean isReady();

}

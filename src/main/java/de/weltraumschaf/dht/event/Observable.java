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
package de.weltraumschaf.dht.event;

/**
 * This interface represents an observable object.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface Observable {

    /**
     * Adds an observer to the set of observers for this object, provided that it is not the same as some observer
     * already in the set. The order in which notifications will be delivered to multiple observers is not specified.
     *
     * @param o an observer to be added
     * CHECKSTYLE:OFF
     * @throws NullPointerException if, the parameter o is null.
     * CHECKSTYLE:ON
     */
    void addObserver(Observer o);

    /**
     * Returns the number of observers of this <tt>Observable</tt> object.
     *
     * @return the number of observers of this object.
     */
    int countObservers();

    /**
     * Deletes an observer from the set of observers of this object.
     *
     * @param observer must not be {@code null}
     */
    void deleteObserver(Observer observer);

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    void deleteObservers();

    /**
     * Tests if this object has changed.
     *
     * @return true if object has changed, else false
     */
    boolean hasChanged();

    /**
     * If this object has changed, as indicated by the {@link #hasChanged()} method, then notify all of its observers.
     *
     * Each observer has its {@link Observer#update(de.weltraumschaf.dht.event.Event)} method called with one
     * event arguments.
     *
     * @param event must not be {@code null}
     */
    void notifyObservers(Event event);

}

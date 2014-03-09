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
package de.weltraumschaf.dht;

import edu.umd.cs.mtc.MultithreadedTest;
import edu.umd.cs.mtc.TestFramework;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FooTest extends MultithreadedTest {

    @Override
    public void initialize() {

    }

    public void thread1() {

    }

    public void thread2() {

    }

    @Override
    public void finish() {

    }

    @Test
    public void testCase() throws Throwable {
        TestFramework.runOnce(new FooTest());
    }

    // Fixtures:

    static class Singleton {

        private static Singleton INSTACNE;

        private Singleton() {
            readProperties();
        }

        public static Singleton getInstance() {
            if (null != INSTACNE) {
                return INSTACNE;
            }

            return INSTACNE = new Singleton();
        }

        private void readProperties() {
            loadUserProperties();
        }

        private void loadUserProperties() {
            XmlPropertiesLoader.loadUserProperties();
        }

    }

    static class XmlPropertiesLoader {
        static void loadUserProperties() {
            List<Container> source = null;
            List<Container> target = null;
            mergeXmlTags(source, target);
        }

        private static void mergeXmlTags(List<Container> source, List<Container> target) {
        }
    }

    static class Container {
        private List<Container> children = new ArrayList<Container>();
    }
}

/**
 * Copyright (c) 2015-2016 Games Neox. All rights reserved.
 *
 * This file is an original work developed by Games Neox
 */

package com.gamesneox.lazy;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;



/**
 * @author Games Neox (games.neox@gmail.com)
 */
@RunWith(RobolectricTestRunner.class)
public class LazyTest {

    /**
     * invalid ({@code null}) loader provided
     */
    @SuppressWarnings("ConstantConditions")
    @Ignore
    @Test(expected = NullPointerException.class)
    public void testLazy0() {
        new Lazy<String>(null);
    }

    /**
     * correct flow
     */
    @SuppressWarnings("Convert2Diamond")
    @Test
    public void testLazy1() {
        final AtomicInteger loaderCalledTimes = new AtomicInteger(0);

        Lazy<String> lazy = new Lazy<String>(new Lazy.ILoader<String>() {

            @Override
            public String load() {
                loaderCalledTimes.incrementAndGet();

                return "test";
            }
        });

        assertEquals("test", lazy.safeGet());
        assertEquals(lazy.safeGet(), lazy.fastGet());
        assertEquals(1, loaderCalledTimes.get());
    }
}

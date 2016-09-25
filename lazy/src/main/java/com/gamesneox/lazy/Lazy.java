/**
 * Copyright (c) 2015-2016 Games Neox. All rights reserved.
 *
 * This file is an original work developed by Games Neox
 */

package com.gamesneox.lazy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;



/**
 * @author Games Neox (games.neox@gmail.com)
 *
 * This API can be called from any thread. However it's up to the caller to take care of the thread-safety.
 */
@SuppressWarnings("StringBufferReplaceableByString")
public final class Lazy<T> {

    /**
     * @author Games Neox (games.neox@gmail.com)
     */
    @SuppressWarnings("UnnecessaryInterfaceModifier")
    public interface ILoader<T> {

        public @Nullable T load();
    }

    /**
     * @author Games Neox (games.neox@gmail.com)
     */
    @SuppressWarnings("UnnecessaryInterfaceModifier")
    private interface IGetter<T> {

        public @Nullable T get();
    }

    private static final String LOG_TAG = Lazy.class.getSimpleName();

    private IGetter<T> mGetter;

    /**
     * @pre {@code loader} is not {@code null}
     *
     * @param loader object to be called once (thread-safety guaranteed by the {@link Lazy} class) in the very first
     *         {@link #fastGet()}/{@link #safeGet()} invocation
     */
    public Lazy(final @NonNull ILoader<T> loader) {
        // TODO: add preconditions!

        mGetter = new IGetter<T>() {

            @Override
            public @Nullable T get() {
                // TODO: remove logs in release builds!
                Log.v(LOG_TAG, "initial IGetter: Enter");

                final T value = loader.load();

                mGetter = new IGetter<T>() {

                    @Override
                    public @Nullable T get() {
                        Log.v(LOG_TAG,
                                new StringBuilder("target IGetter: Enter/Exit(").append(value).append(")").toString());

                        return value;
                    }
                };

                Log.v(LOG_TAG, new StringBuilder("initial IGetter: Exit(").append(value).append(")").toString());

                return value;
            }
        };
    }

    public synchronized @Nullable T safeGet() {
        return fastGet();
    }

    public @Nullable T fastGet() {
        return mGetter.get();
    }
}

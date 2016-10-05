/**
 * Copyright (c) 2015-2016 Games Neox. All rights reserved.
 *
 * This file is an original work developed by Games Neox
 */

package com.gamesneox.lazy;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.ref.WeakReference;

import static com.gamesneox.defence.Defence.assertNotNull;



/**
 * @author Games Neox (games.neox@gmail.com)
 *
 * This API can be called from any thread. However it's up to the caller to take care of the thread-safety.
 */
@SuppressWarnings({ "Convert2Diamond", "StringBufferReplaceableByString" })
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

    /**
     * @author Games Neox (games.neox@gmail.com)
     */
    private static final class InitialGetter<T> implements IGetter<T> {

        private final @NonNull ILoader<T> mLoader;
        private final @NonNull WeakReference<Lazy<T>> mWeakParent;

        public InitialGetter(@NonNull Lazy<T> parent, @NonNull ILoader<T> loader) {
            mLoader = loader;
            mWeakParent = new WeakReference<Lazy<T>>(parent);
        }

        @Override
        public @Nullable T get() {
            // TODO: remove logs in release builds!
            Log.v(LOG_TAG, "initial IGetter: Enter");

            final T value = mLoader.load();

            mWeakParent.get().mGetter = new TargetGetter<T>(value);

            Log.v(LOG_TAG, new StringBuilder("initial IGetter: Exit(").append(value).append(")").toString());

            return value;
        }
    }

    /**
     * @author Games Neox (games.neox@gmail.com)
     */
    private static final class TargetGetter<T> implements IGetter<T> {

        private final @Nullable T mValue;

        public TargetGetter(@Nullable T value) {
            mValue = value;
        }

        @Override
        public @Nullable T get() {
            Log.v(LOG_TAG, new StringBuilder("target IGetter: Enter/Exit(").append(mValue).append(")").toString());

            return mValue;
        }
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
        assertNotNull(loader, "loader !");

        mGetter = new InitialGetter<T>(this, loader);
    }

    public synchronized @Nullable T safeGet() {
        return fastGet();
    }

    public @Nullable T fastGet() {
        return mGetter.get();
    }
}

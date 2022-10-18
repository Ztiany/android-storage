package com.android.sdk.cache.sp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.sdk.cache.BaseStorage;

class SpStorage extends BaseStorage {

    private final SpCache mSpCache;

    public SpStorage(@NonNull Context context, @NonNull String cacheName, boolean commitImmediately) {
        mSpCache = new SpCache(context, cacheName, commitImmediately);
    }

    @Override
    public void putString(@NonNull String key, @Nullable String value) {
        mSpCache.putString(key, value);
    }

    @Override
    public SharedPreferences.Editor edit() {
        return mSpCache.edit();
    }

    @NonNull
    @Override
    public String getString(@NonNull String key, @NonNull String defaultValue) {
        return mSpCache.getString(key, defaultValue);
    }

    @Nullable
    @Override
    public String getString(@NonNull String key) {
        return mSpCache.getString(key, null);
    }

    @Override
    public void putLong(@NonNull String key, long value) {
        mSpCache.putLong(key, value);
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        return mSpCache.getLong(key, defaultValue);
    }

    @Override
    public void putInt(@NonNull String key, int value) {
        mSpCache.putInt(key, value);
    }

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        return mSpCache.getInt(key, defaultValue);
    }

    @Override
    public void putBoolean(@NonNull String key, boolean value) {
        mSpCache.putBoolean(key, value);
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return mSpCache.getBoolean(key, defaultValue);
    }

    @Override
    public void remove(@NonNull String key) {
        mSpCache.remove(key);
    }

    @Override
    public void clearAll() {
        mSpCache.clear();
    }

}
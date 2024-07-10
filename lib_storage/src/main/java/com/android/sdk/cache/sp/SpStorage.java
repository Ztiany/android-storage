package com.android.sdk.cache.sp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.sdk.cache.BaseStorage;
import com.android.sdk.cache.OnValueChangedListener;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

class SpStorage extends BaseStorage {

    private final SpCache mSpCache;

    private final Map<OnValueChangedListener, SharedPreferences.OnSharedPreferenceChangeListener> mReferenceMap = new ConcurrentHashMap<>();

    public SpStorage(@NonNull Context context, @NonNull String cacheName, boolean commitImmediately) {
        mSpCache = new SpCache(context, cacheName, commitImmediately);
    }

    ///////////////////////////////////////////////////////////////////////////
    // get
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        return mSpCache.getInt(key, defaultValue);
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        return mSpCache.getLong(key, defaultValue);
    }

    @Override
    public float getFloat(@NonNull String key, float defaultValue) {
        return mSpCache.getFloat(key, defaultValue);
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return mSpCache.getBoolean(key, defaultValue);
    }

    @Nullable
    @Override
    public String getString(@NonNull String key) {
        return mSpCache.getString(key, null);
    }

    @NonNull
    @Override
    public String getString(@NonNull String key, @NonNull String defaultValue) {
        return mSpCache.getString(key, defaultValue);
    }

    @Nullable
    @Override
    public Set<String> getStringSet(@NonNull String key) {
        return mSpCache.getStringSet(key, null);
    }

    @NonNull
    @Override
    public Set<String> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        return mSpCache.getStringSet(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // put
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void putInt(@NonNull String key, int value) {
        mSpCache.putInt(key, value);
    }

    @Override
    public void putLong(@NonNull String key, long value) {
        mSpCache.putLong(key, value);
    }

    @Override
    public void putFloat(@NonNull String key, float value) {
        mSpCache.putFloat(key, value);
    }

    @Override
    public void putBoolean(@NonNull String key, boolean value) {
        mSpCache.putBoolean(key, value);
    }

    @Override
    public void putString(@NonNull String key, @Nullable String value) {
        mSpCache.putString(key, value);
    }

    @Override
    public void putStringSet(@NonNull String key, @Nullable Set<String> value) {
        mSpCache.putStringSet(key, value);
    }

    ///////////////////////////////////////////////////////////////////////////
    // other
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public SharedPreferences.Editor edit() {
        return mSpCache.edit();
    }

    @Override
    public void remove(@NonNull String key) {
        mSpCache.remove(key);
    }

    @Override
    public void clearAll() {
        mSpCache.clear();
    }

    @Override
    public void addOnValueChangedListener(OnValueChangedListener listener) {
        if (mReferenceMap.containsKey(listener)) {
            return;
        }
        mSpCache.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {

            {
                mReferenceMap.put(listener, this);
            }

            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
                listener.onValueChanged(SpStorage.this, key);
            }
        });
    }

    @Override
    public void removeOnValueChangedListener(OnValueChangedListener listener) {
        SharedPreferences.OnSharedPreferenceChangeListener pairedListener = mReferenceMap.remove(listener);
        if (pairedListener != null) {
            mSpCache.unregisterOnSharedPreferenceChangeListener(pairedListener);
        }
    }

}
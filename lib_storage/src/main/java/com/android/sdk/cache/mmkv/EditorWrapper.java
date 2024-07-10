package com.android.sdk.cache.mmkv;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.android.sdk.cache.Storage;

import java.util.Set;

class EditorWrapper implements SharedPreferences.Editor {

    private final SharedPreferences.Editor mOriginalEditor;

    private final Storage mStorage;

    EditorWrapper(SharedPreferences.Editor editor, MMKVStorage mmkvStorage) {
        mOriginalEditor = editor;
        mStorage = mmkvStorage;
    }

    @Override
    public SharedPreferences.Editor putString(String key, @Nullable String value) {
        mStorage.putString(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putStringSet(String key, @Nullable Set<String> values) {
        mStorage.putStringSet(key, values);
        return this;
    }

    @Override
    public SharedPreferences.Editor putInt(String key, int value) {
        mStorage.putInt(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putLong(String key, long value) {
        mStorage.putLong(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putFloat(String key, float value) {
        mStorage.putFloat(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor putBoolean(String key, boolean value) {
        mStorage.putBoolean(key, value);
        return this;
    }

    @Override
    public SharedPreferences.Editor remove(String key) {
        mStorage.remove(key);
        return this;
    }

    @Override
    public SharedPreferences.Editor clear() {
        mStorage.clearAll();
        return this;
    }

    @Override
    public boolean commit() {
        return mOriginalEditor.commit();
    }

    @Override
    public void apply() {
        mOriginalEditor.apply();
    }

}
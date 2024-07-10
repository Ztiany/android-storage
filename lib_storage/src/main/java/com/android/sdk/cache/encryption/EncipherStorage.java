package com.android.sdk.cache.encryption;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.sdk.cache.BaseStorage;
import com.android.sdk.cache.OnValueChangedListener;
import com.android.sdk.cache.Storage;

import java.util.Set;

/**
 * @author Ztiany
 */
public class EncipherStorage extends BaseStorage {

    private final Storage mStorage;
    private final Encipher mEncipher;

    public EncipherStorage(Storage storage, Encipher encipher) {
        mStorage = storage;
        mEncipher = encipher;
    }

    ///////////////////////////////////////////////////////////////////////////
    // get
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        return mStorage.getInt(key, defaultValue);
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        return mStorage.getLong(key, defaultValue);
    }

    @Override
    public float getFloat(@NonNull String key, float defaultValue) {
        return mStorage.getFloat(key, defaultValue);
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return mStorage.getBoolean(key, defaultValue);
    }

    @Nullable
    @Override
    public String getString(@NonNull String key) {
        return mEncipher.decrypt(mStorage.getString(key));
    }

    @NonNull
    @Override
    public String getString(@NonNull String key, @NonNull String defaultValue) {
        return mEncipher.decrypt(mStorage.getString(key, defaultValue));
    }

    @Nullable
    @Override
    public Set<String> getStringSet(@NonNull String key) {
        return mStorage.getStringSet(key);
    }

    @NonNull
    @Override
    public Set<String> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        return mStorage.getStringSet(key, defaultValue);
    }

    ///////////////////////////////////////////////////////////////////////////
    // put
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void putInt(@NonNull String key, int value) {
        mStorage.putInt(key, value);
    }

    @Override
    public void putLong(@NonNull String key, long value) {
        mStorage.putLong(key, value);
    }

    @Override
    public void putFloat(@NonNull String key, float value) {
        mStorage.putFloat(key, value);
    }

    @Override
    public void putBoolean(@NonNull String key, boolean value) {
        mStorage.putBoolean(key, value);
    }

    @Override
    public void putString(@NonNull String key, @Nullable String value) {
        mStorage.putString(key, mEncipher.encrypt(value));
    }

    @Override
    public void putStringSet(@NonNull String key, @Nullable Set<String> value) {
        mStorage.putStringSet(key, value);
    }

    ///////////////////////////////////////////////////////////////////////////
    // other
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void remove(@NonNull String key) {
        mStorage.remove(key);
    }

    @Override
    public void clearAll() {
        mStorage.clearAll();
    }

    @Override
    public SharedPreferences.Editor edit() {
        return mStorage.edit();
    }

    @Override
    public void addOnValueChangedListener(OnValueChangedListener listener) {
        mStorage.addOnValueChangedListener(listener);
    }

    @Override
    public void removeOnValueChangedListener(OnValueChangedListener listener) {
        mStorage.removeOnValueChangedListener(listener);
    }

}
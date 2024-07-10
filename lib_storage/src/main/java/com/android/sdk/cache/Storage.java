package com.android.sdk.cache;


import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * An interface for storage.
 *
 * @author Ztiany
 */
public interface Storage {

    ///////////////////////////////////////////////////////////////////////////
    // Putting
    ///////////////////////////////////////////////////////////////////////////
    void putInt(@NonNull String key, int value);

    void putLong(@NonNull String key, long value);

    void putFloat(@NonNull String key, float value);

    void putBoolean(@NonNull String key, boolean value);

    void putString(@NonNull String key, @Nullable String value);

    void putStringSet(@NonNull String key, @Nullable Set<String> value);

    void putEntity(@NonNull String key, @Nullable Object entity, long cacheTime);

    void putEntity(@NonNull String key, @Nullable Object entity);

    SharedPreferences.Editor edit();

    ///////////////////////////////////////////////////////////////////////////
    // Getting
    ///////////////////////////////////////////////////////////////////////////
    int getInt(@NonNull String key, int defaultValue);

    float getFloat(@NonNull String key, float defaultValue);

    long getLong(@NonNull String key, long defaultValue);

    boolean getBoolean(@NonNull String key, boolean defaultValue);

    @Nullable
    String getString(@NonNull String key);

    @NonNull
    String getString(@NonNull String key, @NonNull String defaultValue);

    @Nullable
    Set<String> getStringSet(@NonNull String key);

    @NonNull
    Set<String> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue);

    /**
     * @param type you may need to use {@link TypeFlag} to get the type.
     */
    @Nullable
    <T> T getEntity(@NonNull String key, @NonNull Type type);

    /**
     * @param type you may need to use {@link TypeFlag} to get the type.
     */
    @NonNull
    <T> T getEntity(@NonNull String key, @NonNull Type type, @NonNull T defaultValue);

    ///////////////////////////////////////////////////////////////////////////
    // remove
    ///////////////////////////////////////////////////////////////////////////

    void remove(@NonNull String key);

    void clearAll();

    ///////////////////////////////////////////////////////////////////////////
    // listener
    ///////////////////////////////////////////////////////////////////////////

    void addOnValueChangedListener(OnValueChangedListener listener);

    void removeOnValueChangedListener(OnValueChangedListener listener);

}
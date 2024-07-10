package com.android.sdk.cache.mmkv;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.sdk.cache.BaseStorage;
import com.android.sdk.cache.OnValueChangedListener;
import com.tencent.mmkv.MMKV;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

/**
 * @author Ztiany
 */
class MMKVStorage extends BaseStorage {

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

    private final List<OnValueChangedListener> mOnValueChangedListeners = new CopyOnWriteArrayList<>();

    private final MMKV mMmkv;

    public MMKVStorage(Context context, String mmkvId) {
        this(context, mmkvId, false);
    }

    public MMKVStorage(Context context, String mmkvId, boolean multiProcess) {
        if (INITIALIZED.compareAndSet(false, true)) {
            String rootDir = MMKV.initialize(context.getApplicationContext());
            Timber.d("MMKV initialized and rootDir is: %s", rootDir);
        }

        int mode = multiProcess ? MMKV.MULTI_PROCESS_MODE : MMKV.SINGLE_PROCESS_MODE;
        mMmkv = MMKV.mmkvWithID(mmkvId, mode);
    }

    ///////////////////////////////////////////////////////////////////////////
    // get
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        try {
            return mMmkv.decodeInt(key, defaultValue);
        } catch (Exception e) {
            Timber.e(e, "getInt error");
        }
        return defaultValue;
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        try {
            return mMmkv.decodeLong(key, defaultValue);
        } catch (Exception e) {
            Timber.e(e, "getLong error");
        }
        return defaultValue;
    }

    @Override
    public float getFloat(@NonNull String key, float defaultValue) {
        try {
            return mMmkv.decodeFloat(key, defaultValue);
        } catch (Exception e) {
            Timber.e(e, "getFloat error");
        }
        return defaultValue;
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        try {
            return mMmkv.decodeBool(key, defaultValue);
        } catch (Exception e) {
            Timber.e(e, "getBoolean error");
        }
        return defaultValue;
    }

    @NonNull
    @Override
    public String getString(@NonNull String key, @NonNull String defaultValue) {
        String result = null;
        try {
            result = mMmkv.decodeString(key, defaultValue);
        } catch (Exception e) {
            Timber.e(e, "getString error");
        }
        return result == null ? defaultValue : result;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(@NonNull String key) {
        Set<String> result = null;
        try {
            result = mMmkv.decodeStringSet(key);
        } catch (Exception e) {
            Timber.e(e, "getString error");
        }
        return result;
    }

    @NonNull
    @Override
    public Set<String> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        Set<String> result = null;
        try {
            result = mMmkv.decodeStringSet(key);
        } catch (Exception e) {
            Timber.e(e, "getString error");
        }
        return result == null ? defaultValue : result;
    }

    @Nullable
    @Override
    public String getString(@NonNull String key) {
        try {
            return mMmkv.decodeString(key);
        } catch (Exception e) {
            Timber.e(e, "getString error");
        }
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // put
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void putInt(@NonNull String key, int value) {
        try {
            int oldValue = getInt(key, Integer.MIN_VALUE);
            mMmkv.encode(key, value);
            if (oldValue != value) {
                notifyValueChanged(key);
            }
        } catch (Exception e) {
            Timber.e(e, "putInt error");
        }
    }

    @Override
    public void putLong(@NonNull String key, long value) {
        try {
            long oldValue = getLong(key, Long.MIN_VALUE);
            mMmkv.encode(key, value);
            if (oldValue != value) {
                notifyValueChanged(key);
            }
        } catch (Exception e) {
            Timber.e(e, "putLong error");
        }
    }

    @Override
    public void putFloat(@NonNull String key, float value) {
        try {
            float oldValue = getFloat(key, Long.MIN_VALUE);
            mMmkv.encode(key, value);
            if (oldValue != value) {
                notifyValueChanged(key);
            }
        } catch (Exception e) {
            Timber.e(e, "putFloat error");
        }
    }

    @Override
    public void putBoolean(@NonNull String key, boolean value) {
        try {
            boolean oldValue = getBoolean(key, false);
            mMmkv.encode(key, value);
            if (oldValue != value) {
                notifyValueChanged(key);
            }
        } catch (Exception e) {
            Timber.e(e, "putBoolean error");
        }
    }

    @Override
    public void putString(@NonNull String key, @Nullable String value) {
        try {
            if (value == null) {
                remove(key);
                return;
            }
            String oldValue = getString(key);
            mMmkv.encode(key, value);
            if (!Objects.equals(oldValue, value)) {
                notifyValueChanged(key);
            }
        } catch (Exception e) {
            Timber.e(e, "putString error");
        }
    }

    @Override
    public void putStringSet(@NonNull String key, @Nullable Set<String> value) {
        try {
            if (value == null) {
                remove(key);
                return;
            }
            Set<String> oldValue = getStringSet(key);
            mMmkv.encode(key, value);
            if (!Objects.equals(oldValue, value)) {
                notifyValueChanged(key);
            }
        } catch (Exception e) {
            Timber.e(e, "putStringSet error");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // other
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void remove(@NonNull String key) {
        try {
            boolean contains = mMmkv.contains(key);
            mMmkv.removeValueForKey(key);
            if (contains) {
                notifyValueChanged(key);
            }
        } catch (Exception e) {
            Timber.e(e, "remove error");
        }
    }

    @Override
    public void clearAll() {
        String[] keys = mMmkv.allNonExpireKeys();
        mMmkv.clear();
        notifyValuesChanged(keys);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public SharedPreferences.Editor edit() {
        return new EditorWrapper(mMmkv.edit(), this);
    }

    private void notifyValuesChanged(@Nullable String[] keys) {
        if (keys == null) {
            return;
        }
        for (String key : keys) {
            notifyValueChanged(key);
        }
    }

    private void notifyValueChanged(String key) {
        if (key == null) {
            return;
        }
        for (OnValueChangedListener onValueChangedListener : mOnValueChangedListeners) {
            onValueChangedListener.onValueChanged(this, key);
        }
    }

    @Override
    public void addOnValueChangedListener(OnValueChangedListener listener) {
        if (mOnValueChangedListeners.contains(listener)) {
            return;
        }
        mOnValueChangedListeners.add(listener);
    }

    @Override
    public void removeOnValueChangedListener(OnValueChangedListener listener) {
        mOnValueChangedListeners.remove(listener);
    }

}
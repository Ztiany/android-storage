package com.android.sdk.cache.mmkv;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.sdk.cache.BaseStorage;
import com.tencent.mmkv.MMKV;

import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;

/**
 * @author Ztiany
 */
@SuppressWarnings("WeakerAccess,unused")
class MMKVStorage extends BaseStorage {

    private static final String TAG = MMKVStorage.class.getSimpleName();

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

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

    @Override
    public void putString(@NonNull String key, @Nullable String value) {
        try {
            if (value == null) {
                remove(key);
                return;
            }
            mMmkv.encode(key, value);
        } catch (Error error) {
            error.printStackTrace();
        }
    }

    @Override
    public SharedPreferences.Editor edit() {
        Timber.w("MMKV doesn't support editor.");
        return mMmkv.edit();
    }

    @NonNull
    @Override
    public String getString(@NonNull String key, @NonNull String defaultValue) {
        String result = null;
        try {
            result = mMmkv.decodeString(key, defaultValue);
        } catch (Error error) {
            error.printStackTrace();
        }
        return result == null ? defaultValue : result;
    }

    @Nullable
    @Override
    public String getString(@NonNull String key) {
        try {
            return mMmkv.decodeString(key);
        } catch (Error error) {
            error.printStackTrace();
        }
        return null;
    }

    @Override
    public void putLong(@NonNull String key, long value) {
        try {
            mMmkv.encode(key, value);
        } catch (Error error) {
            error.printStackTrace();
        }
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        try {
            return mMmkv.decodeLong(key, defaultValue);
        } catch (Error error) {
            error.printStackTrace();
        }
        return defaultValue;
    }

    @Override
    public void putInt(@NonNull String key, int value) {
        try {
            mMmkv.encode(key, value);
        } catch (Error error) {
            error.printStackTrace();
        }
    }

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        try {
            return mMmkv.decodeInt(key, defaultValue);
        } catch (Error error) {
            error.printStackTrace();
        }
        return defaultValue;
    }

    @Override
    public void putBoolean(@NonNull String key, boolean value) {
        try {
            mMmkv.encode(key, value);
        } catch (Error error) {
            error.printStackTrace();
        }
    }

    @Override
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        try {
            return mMmkv.decodeBool(key, defaultValue);
        } catch (Error error) {
            error.printStackTrace();
        }
        return defaultValue;
    }

    @Override
    public void remove(@NonNull String key) {
        try {
            mMmkv.removeValueForKey(key);
        } catch (Error error) {
            error.printStackTrace();
        }
    }

    @Override
    public void clearAll() {
        mMmkv.clear();
    }

}
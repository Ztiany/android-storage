package com.android.sdk.cache;

import org.jetbrains.annotations.Nullable;

public interface OnValueChangedListener {

    /**
     * Note: Sometimes, even if the value associated with the key is not changed, this method may be called.
     *
     * @param key when key is null, it means all keys are changed.
     */
    void onValueChanged(Storage storage, @Nullable String key);

}
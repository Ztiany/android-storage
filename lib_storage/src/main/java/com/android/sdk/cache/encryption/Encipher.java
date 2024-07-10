package com.android.sdk.cache.encryption;

import androidx.annotation.Nullable;

/**
 * @author Ztiany
 */
public interface Encipher {

    String encrypt(@Nullable String origin);

    String decrypt(@Nullable String encrypted);

}

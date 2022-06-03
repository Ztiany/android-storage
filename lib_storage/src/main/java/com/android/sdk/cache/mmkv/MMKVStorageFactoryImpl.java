package com.android.sdk.cache.mmkv;

import android.content.Context;

import com.android.sdk.cache.encryption.EncipherStorage;
import com.android.sdk.cache.Storage;
import com.android.sdk.cache.StorageFactory;

/**
 * @author Ztiany
 */
public class MMKVStorageFactoryImpl implements StorageFactory {

    @Override
    public Builder newBuilder(Context context) {
        return new MMKVStorageBuilder(context);
    }

    public static class MMKVStorageBuilder extends Builder {

        MMKVStorageBuilder(Context context) {
            super(context);
        }

        @Override
        public Storage build() {
            MMKVStorageImpl mmkvStorage = new MMKVStorageImpl(context, storageId, multiProcess);
            if (encipher != null) {
                return new EncipherStorage(mmkvStorage, encipher);
            }
            return mmkvStorage;
        }

    }

}

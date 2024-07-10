package com.android.sdk.cache.mmkv;

import android.content.Context;

import com.android.sdk.cache.encryption.EncipherStorage;
import com.android.sdk.cache.Storage;
import com.android.sdk.cache.StorageFactory;

import timber.log.Timber;

/**
 * @author Ztiany
 */
public class MMKVStorageFactory implements StorageFactory {

    @Override
    public Builder newBuilder(Context context) {
        return new MMKVStorageBuilder(context);
    }

    public static class MMKVStorageBuilder extends Builder {

        MMKVStorageBuilder(Context context) {
            super(context);
        }

        @Override
        public Builder commitImmediately(boolean commitImmediately) {
            if (commitImmediately) {
                Timber.w("MMKVStorage was initialized, but the parameter [commitImmediately] is ignored.");
            }
            return super.commitImmediately(commitImmediately);
        }

        @Override
        public Storage build() {
            MMKVStorage mmkvStorage = new MMKVStorage(context, storageId, multiProcess);
            if (encipher != null) {
                return new EncipherStorage(mmkvStorage, encipher);
            }
            return mmkvStorage;
        }

    }

}
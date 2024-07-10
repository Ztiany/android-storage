package com.android.sdk.cache.sp;

import android.content.Context;

import com.android.sdk.cache.Storage;
import com.android.sdk.cache.StorageFactory;
import com.android.sdk.cache.encryption.EncipherStorage;

import timber.log.Timber;

/**
 * @author Ztiany
 */
public class SpStorageFactory implements StorageFactory {

    @Override
    public Builder newBuilder(Context context) {
        return new SpStorageFactoryBuilder(context);
    }

    public static class SpStorageFactoryBuilder extends Builder {

        SpStorageFactoryBuilder(Context context) {
            super(context);
        }

        @Override
        public Builder enableMultiProcess(boolean multiProcess) {
            if (multiProcess) {
                Timber.w("SpStorage was initialized, but the parameter [multiProcess] is ignored.");
            }
            super.enableMultiProcess(multiProcess);
            return this;
        }

        @Override
        public Storage build() {
            SpStorage storage = new SpStorage(context, storageId, commitImmediately);
            if (encipher != null) {
                return new EncipherStorage(storage, encipher);
            }
            return storage;
        }

    }

}
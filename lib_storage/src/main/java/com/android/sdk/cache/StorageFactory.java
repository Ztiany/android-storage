package com.android.sdk.cache;

import android.content.Context;

import com.android.sdk.cache.encryption.Encipher;

/**
 * @author Ztiany
 */
public interface StorageFactory {

    StorageFactory.Builder newBuilder(Context context);

    abstract class Builder {

        protected Context context;
        protected String storageId;
        protected boolean multiProcess;
        protected boolean commitImmediately;
        protected Encipher encipher;

        protected Builder(Context context) {
            this.context = context;
        }

        /**
         * Note: SP does not support multi-process.
         */
        public Builder enableMultiProcess(boolean multiProcess) {
            this.multiProcess = multiProcess;
            return this;
        }

        public Builder storageId(String storageId) {
            this.storageId = storageId;
            return this;
        }

        /**
         * @param encipher if you want to encrypt the data. Note: only String or Entity type can be encrypted.
         */
        public Builder encipher(Encipher encipher) {
            this.encipher = encipher;
            return this;
        }

        /**
         * if set to true, the data will be synchronized to the disk immediately after each operation.
         */
        public Builder commitImmediately(boolean commitImmediately) {
            this.commitImmediately = commitImmediately;
            return this;
        }

        public abstract Storage build();

    }

}
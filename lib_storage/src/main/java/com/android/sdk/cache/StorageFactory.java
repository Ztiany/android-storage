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
         * 是否允许跨进程访问存储
         */
        public Builder enableMultiProcess(boolean multiProcess) {
            this.multiProcess = multiProcess;
            return this;
        }

        /**
         * @param storageId 存储标识
         */
        public Builder storageId(String storageId) {
            this.storageId = storageId;
            return this;
        }

        /**
         * @param encipher 数据加密接口
         */
        public Builder encipher(Encipher encipher) {
            this.encipher = encipher;
            return this;
        }

        public Builder commitImmediately(boolean commitImmediately) {
            this.commitImmediately = commitImmediately;
            return this;
        }

        public abstract Storage build();
    }

}
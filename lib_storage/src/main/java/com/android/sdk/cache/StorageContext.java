package com.android.sdk.cache;

import com.android.sdk.cache.json.Serializer;
import com.android.sdk.cache.mmkv.MMKVStorageFactory;
import com.android.sdk.cache.sp.SpStorageFactory;

/**
 * @author Ztiany
 */
public class StorageContext {

    public static final String MMKV = "MMKV";
    public static final String SP = "SP";

    private static Serializer sSerializer;

    public static void setSerializer(Serializer serializer) {
        sSerializer = serializer;
    }

    public static Serializer provideSerializer() {
        if (sSerializer != null) {
            return sSerializer;
        }
        return Holder.JSON_SERIALIZER;
    }

    private static class Holder {
        private static final Serializer JSON_SERIALIZER = new GsonSerializer();
    }

    /**
     * @param type {@link #MMKV} or {@link  #SP}.
     */
    public static StorageFactory newStorageFactory(@StorageType String type) {
        if (MMKV.equals(type)) {
            return new MMKVStorageFactory();
        } else if (SP.equals(type)) {
            return new SpStorageFactory();
        }
        throw new IllegalArgumentException("Unsupported type: " + type);
    }

}
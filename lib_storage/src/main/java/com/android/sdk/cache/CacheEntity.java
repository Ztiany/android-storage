package com.android.sdk.cache;

class CacheEntity {

    String mJsonData;

    long mCacheTime;

    long mStoreTime;

    CacheEntity(String jsonData, long cacheTime) {
        mJsonData = jsonData;
        mCacheTime = cacheTime;
        mStoreTime = System.currentTimeMillis();
    }

}
package com.android.sdk.cache

import androidx.annotation.StringDef

@StringDef(
    value = [StorageContext.SP, StorageContext.MMKV]
)
@Retention(AnnotationRetention.SOURCE)
annotation class StorageType
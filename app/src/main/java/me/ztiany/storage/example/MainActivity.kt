package me.ztiany.storage.example

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.sdk.cache.StorageContext
import com.android.sdk.cache.apply
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity() {

    private val mmkvStorage by lazy {
        StorageContext.newStorageFactory(StorageContext.MMKV).newBuilder(this)..storageId("mmkv-file").build()
    }

    private val spStorage by lazy {
        StorageContext.newStorageFactory(StorageContext.SP).newBuilder(this).storageId("sp-file").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(DebugTree())
    }

    fun get(view: View) {
        Timber.d("MMVK result %s", mmkvStorage.getString("key", "Default Value"))
        Timber.d("MMVK result %s", mmkvStorage.getString("A", "Default Value"))
        Timber.d("MMVK result %s", mmkvStorage.getString("B", "Default Value"))
        Timber.d("MMVK result %s", mmkvStorage.getString("C", "Default Value"))
        Timber.d("MMVK result %s", mmkvStorage.getString("D", "Default Value"))

        Timber.d("DiskLru result %s", spStorage.getString("key", "Default Value"))
        Timber.d("DiskLru result %s", spStorage.getString("A", "Default Value"))
        Timber.d("DiskLru result %s", spStorage.getString("B", "Default Value"))
        Timber.d("DiskLru result %s", spStorage.getString("C", "Default Value"))
        Timber.d("DiskLru result %s", spStorage.getString("D", "Default Value"))
    }

    fun save(view: View) {
        mmkvStorage.putString("key", "Saved Value")
        spStorage.putString("key", "Saved Value")

        mmkvStorage.apply {
            putString("A", "A")
            putString("B", "B")
            putString("C", "C")
            putString("D", "D")
        }

        spStorage.apply {
            putString("A", "A")
            putString("B", "B")
            putString("C", "C")
            putString("D", "D")
        }
    }

}
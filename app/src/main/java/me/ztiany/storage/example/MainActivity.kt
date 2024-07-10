package me.ztiany.storage.example

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.sdk.cache.StorageContext
import com.android.sdk.cache.applyEditing
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity() {

    private val mmkvStorage by lazy {
        StorageContext.newStorageFactory(StorageContext.MMKV).newBuilder(this).storageId("mmkv-file").build()
    }

    private val spStorage by lazy {
        StorageContext.newStorageFactory(StorageContext.SP).newBuilder(this).storageId("sp-file").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(DebugTree())


        mmkvStorage.addOnValueChangedListener { storage, key ->
            Timber.d("MMKV key %s changed", key)
        }

        spStorage.addOnValueChangedListener { storage, key ->
            Timber.d("SP key %s changed", key)
        }
    }

    fun clear(view: View) {
        mmkvStorage.clearAll()
        spStorage.clearAll()
    }

    fun get(view: View) {
        Timber.d("MMVK result %s", mmkvStorage.getString("A", "Default Value"))
        Timber.d("MMVK result %s", mmkvStorage.getString("B", "Default Value"))
        Timber.d("MMVK result %s", mmkvStorage.getString("C", "Default Value"))
        Timber.d("MMVK result %s", mmkvStorage.getString("D", "Default Value"))
        Timber.d("MMVK result %s", mmkvStorage.getString("E", "Default Value"))

        Timber.d("SP result %s", spStorage.getString("A", "Default Value"))
        Timber.d("SP result %s", spStorage.getString("B", "Default Value"))
        Timber.d("SP result %s", spStorage.getString("C", "Default Value"))
        Timber.d("SP result %s", spStorage.getString("D", "Default Value"))
        Timber.d("SP result %s", spStorage.getString("E", "Default Value"))
    }

    fun remove(view: View) {
        mmkvStorage.remove("A")
        mmkvStorage.remove("B")
        mmkvStorage.remove("C")
        mmkvStorage.applyEditing {
            remove("D")
            remove("E")
        }

        spStorage.remove("A")
        spStorage.remove("B")
        spStorage.remove("C")
        spStorage.applyEditing {
            remove("D")
            remove("E")
        }
    }

    fun save(view: View) {

        mmkvStorage.applyEditing {
            putString("A", "A")
            putString("B", "B")
            putString("C", "C")
            putString("D", "D")
        }
        mmkvStorage.putString("E", "Saved Value")

        spStorage.applyEditing {
            putString("A", "A")
            putString("B", "B")
            putString("C", "C")
            putString("D", "D")
        }
        spStorage.putString("E", "Saved Value")
    }

}
package me.ztiany.storage.example

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.sdk.cache.mmkv.MMKVStorageFactoryImpl
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity() {

    private val mmvkStorage by lazy {
        MMKVStorageFactoryImpl().newBuilder(this).storageId("test").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(DebugTree())
    }

    fun get(view: View) {
        Toast.makeText(this, mmvkStorage.getString("key", "Default Value"), Toast.LENGTH_LONG).show()
    }

    fun save(view: View) {
        mmvkStorage.putString("key", "Saved Value")
    }

}
package com.android.sdk.cache

import android.content.SharedPreferences.Editor

inline fun <reified T> Storage.getEntity(key: String): T? {
    return this.getEntity(key, object : TypeFlag<T>() {}.type)
}

inline fun <reified T : Any> Storage.getEntity(key: String, defaultValue: T): T {
    return this.getEntity(key, object : TypeFlag<T>() {}.type, defaultValue)
}

fun Storage.commitEditing(editing: Editor.() -> Unit) {
    val edit = edit()
    edit.editing()
    edit.commit()
}

fun Storage.applyEditing(editing: Editor.() -> Unit) {
    val edit = edit()
    edit.editing()
    edit.apply()
}
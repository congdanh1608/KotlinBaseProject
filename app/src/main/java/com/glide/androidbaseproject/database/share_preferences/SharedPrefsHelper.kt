package com.glide.androidbaseproject.database.share_preferences

import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * Created by danhtran on 19/05/2016.
 */
class SharedPrefsHelper(private val sharedPreferences: SharedPreferences) {

    fun writeBoolean(key: String, b: Boolean?) {
        sharedPreferences.edit().putBoolean(key, b!!).apply()
    }

    fun readBoolean(key: String): Boolean? {
        return sharedPreferences.getBoolean(key, false)
    }

    fun writeString(key: String, string: String) {
        sharedPreferences.edit().putString(key, string).apply()
    }

    fun removeKey(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun readString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun writeInt(key: String, i: Int) {
        sharedPreferences.edit().putInt(key, i).apply()
    }

    fun readInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun writeLong(key: String, i: Long) {
        sharedPreferences.edit().putLong(key, i).apply()
    }

    fun readLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    fun clearKey(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun <T : Any> writeObject(key: String, b: T?) {
        if (b == null) {
            return
        }
        synchronized(this) {
            val gson = Gson()
            val json = gson.toJson(b, b.javaClass)
            writeString(key, json)
        }
    }

    fun <T> readObject(key: String, aClass: Class<T>): T? {
        synchronized(this) {
            val gson = Gson()
            val json = readString(key) ?: return null
            return gson.fromJson(json, aClass)
        }
    }
}

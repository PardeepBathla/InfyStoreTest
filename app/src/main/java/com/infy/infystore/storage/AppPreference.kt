package com.infy.infystore.storage

import android.content.SharedPreferences
import com.infy.infystore.InfyStoreApplication
import com.infy.infystore.utils.GlobalConstants

class Preference {
    private val PREFERENCE_KEY: String = GlobalConstants.APP_PREFERENCE
    fun isExists(keyName: String?): Boolean {
        val pref: SharedPreferences =
            InfyStoreApplication.getInstance()!!.getSharedPreferences(PREFERENCE_KEY, 0)
        return pref.contains(keyName)
    }

    fun setPreferenceString(key: String?, value: String?) {
        val pref: SharedPreferences = InfyStoreApplication.getInstance()!!
            .getSharedPreferences(PREFERENCE_KEY, 0) // 0 - for private mode
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setPreferenceBoolean(key: String?, value: Boolean) {
        val pref: SharedPreferences = InfyStoreApplication.getInstance()!!
            .getSharedPreferences(PREFERENCE_KEY, 0) // 0 - for private mode
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun setPreferenceInteger(key: String?, value: Int) {
        val pref: SharedPreferences = InfyStoreApplication.getInstance()!!
            .getSharedPreferences(PREFERENCE_KEY, 0) // 0 - for private mode
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getPreferenceString(key: String?): String? {
        val pref: SharedPreferences =
            InfyStoreApplication.getInstance()!!.getSharedPreferences(PREFERENCE_KEY, 0)
        return pref.getString(key, "")
    }

    fun getPreferenceInteger(key: String?): Int {
        val pref: SharedPreferences =
            InfyStoreApplication.getInstance()!!.getSharedPreferences(PREFERENCE_KEY, 0)
        return pref.getInt(key, -1)
    }

    fun getPreferenceInt(key: String?): Int {
        val pref: SharedPreferences =
            InfyStoreApplication.getInstance()!!.getSharedPreferences(PREFERENCE_KEY, 0)
        return pref.getInt(key, 0)
    }

    fun getPreferenceBoolean(key: String?): Boolean {
        val pref: SharedPreferences = InfyStoreApplication.getInstance()!!.getSharedPreferences(PREFERENCE_KEY, 0)
        return pref.getBoolean(key, false)
    }

    fun clearPreferences() {
        val pref: SharedPreferences =
            InfyStoreApplication.getInstance()!!.getSharedPreferences(PREFERENCE_KEY, 0)
        pref.edit().clear().apply()
    }

    companion object {
        var preference: Preference? = null
        val instance: Preference
            get() = if (preference == null) Preference().also {
                preference = it
            } else preference!!
    }
}
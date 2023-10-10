package com.sane.myapplication

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {
    private const val USER_PREFERENCES = "userPreferences"
    const val PREF_TOKEN = "$USER_PREFERENCES.token"
    const val PREF_USERID = "$USER_PREFERENCES.user_id"
    fun setPreference(context: Context, mobileNumber: String?, details: String?) {
        val editor = getEditor(context)
        editor.putString(mobileNumber, details)
        editor.apply()
    }

    fun setIntPreference(context: Context, mobileNumber: String?, details: Int) {
        val editor = getEditor(context)
        editor.putInt(mobileNumber, details)
        editor.apply()
    }

    private fun getEditor(context: Context): SharedPreferences.Editor {
        val preferences = getSharedPreferences(context)
        return preferences.edit()
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getPreference(context: Context, mobileNumber: String?): String? {
        val preferences = getSharedPreferences(context)
        return preferences.getString(mobileNumber, "")
    }

    fun getIntPreference(context: Context, mobileNumber: String?): Int {
        val preferences = getSharedPreferences(context)
        return preferences.getInt(mobileNumber, 0)
    }

    fun signOut(context: Context) {
        val editor = getEditor(context)
        editor.remove(USER_PREFERENCES)
        editor.remove(PREF_TOKEN)
        editor.remove(PREF_USERID)
        editor.apply()
        editor.clear()
    }
}
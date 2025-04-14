package com.uxharshit.dinevibes.data.local

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context : Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("jwt_token", null)
    }

    fun clearData() {
        prefs.edit().clear().apply()
    }
}
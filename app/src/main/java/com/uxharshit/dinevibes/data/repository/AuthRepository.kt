package com.uxharshit.dinevibes.data.repository

import android.content.Context
import com.google.gson.JsonObject
import com.uxharshit.dinevibes.data.local.UserPreferences
import com.uxharshit.dinevibes.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class AuthRepository(context: Context) {
    private val userPrefs = UserPreferences(context)

    suspend fun login(email: String, password: String) : Response<JsonObject> {
        return withContext(Dispatchers.IO) {
            val response =
                ApiService.instance.login(mapOf("email" to email, "password" to password))
            if (response.isSuccessful) {
                val token = response.body()?.get("token")?.asString
                token?.let {
                    userPrefs.saveToken(it)
                }
            }
            response
        }
    }

    suspend fun verify(token: String) : Response<JsonObject> {
        return withContext(Dispatchers.IO) {
            ApiService.instance.verify(mapOf("token" to token))
        }
    }

    suspend fun register(name: String, email: String, password: String, phoneNumber: String,gender : String) : Response<JsonObject> {
        return withContext(Dispatchers.IO) {
            ApiService.instance.register(
                mapOf(
                    "name" to name,
                    "email" to email,
                    "password" to password,
                    "phonenumber" to phoneNumber,
                    "gender" to gender
                )
            )
        }
    }

    suspend fun forgotPassword(email: String) : Response<JsonObject> {
        return withContext(Dispatchers.IO) {
            ApiService.instance.forgotPassword(mapOf("email" to email))
        }
    }

    fun logout() {
        userPrefs.clearData()
    }

    fun getToken(): String? {
        return userPrefs.getToken()
    }
}
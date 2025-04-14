package com.uxharshit.dinevibes.data.repository

import android.content.Context
import com.google.gson.JsonObject
import com.uxharshit.dinevibes.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class MaintenanceRepository(context: Context) {

    suspend fun uploadMaintenanceData(
        token : String,
        description : RequestBody,
        location : RequestBody,
        category : RequestBody,
        priority :RequestBody,
        imageUrl: List<MultipartBody.Part>,
    ): Response<JsonObject>{
        return withContext(Dispatchers.IO) {

            ApiService.instance.uploadMaintenanceData(
                token,
                description,
                location,
                category,
                priority,
                imageUrl
                )
        }
    }

    suspend fun getUserMaintenance(
        token: String
    ): Response<JsonObject> {
        return withContext(Dispatchers.IO) {
            ApiService.instance.getUserMaintenance(token)
        }
    }
}
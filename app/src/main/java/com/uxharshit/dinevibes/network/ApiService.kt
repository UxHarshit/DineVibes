package com.uxharshit.dinevibes.network

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("/login")
    suspend fun login(@Body request: Map<String, String>) : Response<JsonObject>

    @POST("/register")
    suspend fun register(@Body request: Map<String, String>) : Response<JsonObject>

    @POST("/forgot-password")
    suspend fun forgotPassword(@Body request: Map<String, String>) : Response<JsonObject>

    @POST("/verify")
    suspend fun verify(@Body request: Map<String, String>) : Response<JsonObject>


    @Multipart
    @POST("/maintenance")
    suspend fun uploadMaintenanceData(
        @Header("Authorization") token: String,
        @Part("description") description: RequestBody,
        @Part("location") location: RequestBody,
        @Part("category") category: RequestBody,
        @Part("priority") priority: RequestBody,
        @Part imageUrl: List<MultipartBody.Part>
    ): Response<JsonObject>


    @GET("/maintenance/user")
    suspend fun getUserMaintenance(
        @Header("Authorization") token: String
    ): Response<JsonObject>

    companion object {
        val instance : ApiService by lazy {
            RetrofitClient.retrofit.create(ApiService::class.java)
        }
    }
}
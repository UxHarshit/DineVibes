package com.uxharshit.dinevibes.data.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val gender : String,
    val token: String // Store JWT token
)

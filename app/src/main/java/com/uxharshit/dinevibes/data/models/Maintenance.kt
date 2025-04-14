package com.uxharshit.dinevibes.data.models

data class Maintenance(
    val category: String,
    val description: String,
    val id: String,
    val location: String,
    val priority: String,
    val images : List<String>
)

data class MaintenanceRequest(
    val id: String,
    val description: String,
    val location: String,
    val status: String,
    val category: String,
    val priority: String,
    val createdAt: String,
    val images: List<String>,
)
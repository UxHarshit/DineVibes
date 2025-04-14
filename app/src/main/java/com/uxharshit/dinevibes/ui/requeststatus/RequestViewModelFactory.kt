package com.uxharshit.dinevibes.ui.requeststatus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uxharshit.dinevibes.data.repository.MaintenanceRepository


class RequestViewModelFactory(private val maintenanceRepository: MaintenanceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RequestStatusViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RequestStatusViewModel(maintenanceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



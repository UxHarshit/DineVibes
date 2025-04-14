package com.uxharshit.dinevibes.ui.maintenance

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uxharshit.dinevibes.data.repository.MaintenanceRepository

class MaintenanceViewModelFactory(private val maintenanceRepository: MaintenanceRepository,
        private val app : Application
    ) :  ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MaintenanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MaintenanceViewModel(maintenanceRepository,app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
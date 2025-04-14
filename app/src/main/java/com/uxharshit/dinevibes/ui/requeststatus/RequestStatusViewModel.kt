package com.uxharshit.dinevibes.ui.requeststatus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonArray
import com.uxharshit.dinevibes.data.repository.MaintenanceRepository
import kotlinx.coroutines.launch

class RequestStatusViewModel(private val repository: MaintenanceRepository) :ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> get() = _isLoading

    private val _requestResult = MutableLiveData<Result<JsonArray>>()
    val requestResult : LiveData<Result<JsonArray>> = _requestResult


    suspend fun getRequestStatus(token: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getUserMaintenance(token)
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!.getAsJsonArray("data")
                    if (data.size() > 0) {
                        _requestResult.value = Result.success(data)
                    } else {
                        _requestResult.value = Result.failure(Exception("No data found"))
                    }
                } else {
                    _requestResult.value = Result.failure(Exception("Invalid token"))
                }
            } catch (e: Exception) {
                _requestResult.value = Result.failure(Exception("Something went wrong : ${e.message}"))
            } finally {
                _isLoading.value = false
            }
        }
    }
}
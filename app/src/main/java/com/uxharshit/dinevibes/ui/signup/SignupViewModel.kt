package com.uxharshit.dinevibes.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.uxharshit.dinevibes.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class SignupViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _signupResult = MutableLiveData<Result<String>>() // Success message or error
    val signupResult: LiveData<Result<String>> = _signupResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, email: String, password: String, phonenumber: String, gender : String) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phonenumber.isEmpty() || gender.isEmpty()) {
            _signupResult.value = Result.failure(Exception("All fields are required"))
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = authRepository.register(name, email, password, phonenumber,gender)
                _signupResult.value = if (response.isSuccessful) {
                    Result.success(response.body()?.get("message")?.asString ?: "Registration successful!")
                } else {
                    val errorMessage = response.errorBody()?.string()?.let {
                        runCatching {
                            JsonParser.parseString(it).asJsonObject.get("error").asString
                        }.getOrDefault("Something went wrong")
                    } ?: "Something went wrong"

                    Result.failure(Exception(errorMessage))
                }
            } catch (e: Exception) {
                _signupResult.value = Result.failure(Exception("Something went wrong: ${e.message}"))
            } finally {
                _isLoading.value = false
            }
        }
    }
}
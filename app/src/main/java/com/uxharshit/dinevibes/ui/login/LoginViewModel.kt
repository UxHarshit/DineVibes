package com.uxharshit.dinevibes.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.uxharshit.dinevibes.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<JsonObject>>()
    val loginResult : LiveData<Result<JsonObject>> = _loginResult
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    fun verify(token: String){
        if (token.isEmpty()) {
            _loginResult.value = Result.failure(Exception("Token cannot be empty"))
            return
        }
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response : Response<JsonObject> = authRepository.verify(token)
                if(response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    _loginResult.value = Result.success(body)
                } else {
                    _loginResult.value = Result.failure(Exception("Invalid token"))
                }

            } catch (e: Exception) {
                _loginResult.value = Result.failure(Exception("Something went wrong : ${e.message}"))
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginResult.value = Result.failure(Exception("Email and password cannot be empty"))
            return
        }
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response : Response<JsonObject> = authRepository.login(email, password)
                if(response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    val token = response.body()!!.get("token").asString
                    token?.let {
                        _loginResult.value = Result.success(body)
                    } ?: run {
                        _loginResult.value = Result.failure(Exception("Invalid email or password"))
                    }
                } else {
                    _loginResult.value = Result.failure(Exception("Invalid email or password"))
                }

            } catch (e: Exception) {
                _loginResult.value = Result.failure(Exception("Something went wrong : ${e.message}"))
            } finally {
                _isLoading.value = false
            }
        }

    }
}
package com.uxharshit.dinevibes.ui.maintenance

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.uxharshit.dinevibes.data.repository.AuthRepository
import com.uxharshit.dinevibes.data.repository.MaintenanceRepository
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File

class MaintenanceViewModel(private val maintenanceRepository: MaintenanceRepository,private val app : Application) : ViewModel() {

    private val _maintenanceResult = MutableLiveData<Result<JsonObject>>()
    val maintenanceResult : LiveData<Result<JsonObject>> = _maintenanceResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun submitMaintenanceRequest(
        description: String,
        location : String,
        priority: String,
        category: String,
        images: List<Uri>
    ) {
        if (location.isEmpty() || description.isEmpty() || priority.isEmpty() || category.isEmpty()) {
            _maintenanceResult.value = Result.failure(Exception("All fields are required"))
            return
        }
        if (images.isEmpty()) {
            _maintenanceResult.value = Result.failure(Exception("At least one image is required"))
            return
        }

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val imageParts = images.mapIndexed { index, uri ->
                    val inputStream = app.contentResolver.openInputStream(uri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    inputStream?.close()

                    // Compress the bitmap
                    val outputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // 50% quality
                    val compressedBytes = outputStream.toByteArray()

                    // Write the compressed bytes to a temporary file
                    val file = File.createTempFile("image_$index", ".jpg", app.cacheDir)
                    file.writeBytes(compressedBytes)

                    val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file)
                    MultipartBody.Part.createFormData(
                        "images",
                        file.name,
                        requestFile
                    )
                }

                val response = maintenanceRepository.uploadMaintenanceData(
                    "Bearer ${AuthRepository(app).getToken()}",
                    description.toRequestBody(),
                    location.toRequestBody(),
                    category.toRequestBody(),
                    priority.toRequestBody(),
                    imageParts
                )

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    _maintenanceResult.value = Result.success(body)
                } else {
                    _maintenanceResult.value = Result.failure(Exception("Failed to submit maintenance request"))
                }

            } catch (e: Exception) {
                _maintenanceResult.value = Result.failure(Exception("Something went wrong: ${e.message}"))
                Log.e("MaintenanceViewModel", "Error: $e")
            } finally {
                _isLoading.value = false
            }
        }
    }


    private fun String.toRequestBody(): RequestBody =
        RequestBody.create(MediaType.parse("text/plain"), this)
}
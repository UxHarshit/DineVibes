package com.uxharshit.dinevibes.ui.maintenance

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.get
import com.uxharshit.dinevibes.R
import com.uxharshit.dinevibes.data.repository.AuthRepository
import com.uxharshit.dinevibes.data.repository.MaintenanceRepository
import com.uxharshit.dinevibes.databinding.ActivityMaintenanceBinding
import com.uxharshit.dinevibes.ui.login.LoginViewModel
import com.uxharshit.dinevibes.ui.login.LoginViewModelFactory
import java.io.File
import java.util.Locale

class MaintenanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMaintenanceBinding

    private var cameraImageUri: Uri? = null
    private val selectedImageUris = mutableListOf<Uri>()
    private lateinit var imageContainer: LinearLayout
    private lateinit var progressDialog: AlertDialog

    private var selectedPriority: String? = null
    private var selectedCategory: String? = null

    private fun addViewToGridLayout(imageUri: Uri) {
        val imageView = LayoutInflater.from(this).inflate(R.layout.thumbnail_item,null)
        val image = imageView.findViewById<ImageView>(R.id.img_thumb)
        image.setImageURI(imageUri)
        val close = imageView.findViewById<ImageView>(R.id.img_close)

        close.setOnClickListener {
            imageContainer.removeView(imageView)
            selectedImageUris.remove(imageUri)
        }
        imageContainer.addView(imageView)
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            selectedImageUris.add(it)
            addViewToGridLayout(it)
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && cameraImageUri != null) {
            selectedImageUris.add(cameraImageUri!!)
            addViewToGridLayout(cameraImageUri!!)
        } else {
            Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
        }
    }
    private fun openCamera() {
        val photoFile = File.createTempFile("IMG_", ".jpg", cacheDir)
        val uri = FileProvider.getUriForFile(
            this,
            "${packageName}.fileprovider",
            photoFile
        )
        cameraImageUri = uri
        cameraLauncher.launch(uri)
    }
    private fun showImagePickerDialog() {
        val options = arrayOf("Camera", "Gallery")
        AlertDialog.Builder(this)
            .setTitle("Select Image From")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> galleryLauncher.launch("image/*")
                }
            }.show()
    }
    override fun onStart() {
        super.onStart()
        val token = AuthRepository(this).getToken()
        if (token == null) {
            finish()
        }
    }

    private val maintenanceViewModel: MaintenanceViewModel by viewModels {
        MaintenanceViewModelFactory(MaintenanceRepository(applicationContext), application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaintenanceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageContainer = binding.llUpload
        binding.llToolbar.setOnClickListener {
            finish()
        }

        onBackPressedDispatcher.addCallback(this, object : androidx.activity.OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })


        val gridlayout = binding.gridLayout

        for(i in 0 until gridlayout.childCount){
            val card = gridlayout.getChildAt(i) as LinearLayout
            card.setOnClickListener {
               for (j in 0 until gridlayout.childCount){
                   gridlayout.getChildAt(j).isSelected = false
               }
                card.isSelected = true
                selectedCategory = (card[1] as TextView).text.toString()
            }
        }

        val priorityGrid = binding.priorityGrid
        for(i in 0 until priorityGrid.childCount){
            val card = priorityGrid.getChildAt(i) as LinearLayout
            card.setOnClickListener {
                for (j in 0 until priorityGrid.childCount){
                    priorityGrid.getChildAt(j).isSelected = false
                }
                card.isSelected = true
                selectedPriority = (card[0] as TextView).text.toString()
            }
        }
        progressDialog = AlertDialog.Builder(this)
            .setTitle("Logging in")
            .setMessage("Please wait...")
            .setCancelable(false)
            .create()


        binding.ivUpload.setOnClickListener {
            showImagePickerDialog()
        }

        binding.btnSubmit.setOnClickListener {
            val description = binding.descriptionEditText.text.toString().trim()
            val location = binding.location.text.toString().trim()
            val category = selectedCategory
            val priority = selectedPriority

            maintenanceViewModel.submitMaintenanceRequest(
                description,
                location,
                priority?.lowercase() ?: "",
                category?.lowercase() ?: "",
                selectedImageUris
            )
        }

        maintenanceViewModel.isLoading.observe(this) { isLoading ->
            binding.btnSubmit.isEnabled = !isLoading
            if (isLoading) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        }

        maintenanceViewModel.maintenanceResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Request submitted successfully", Toast.LENGTH_SHORT).show()
                finish()
            }.onFailure {
                Log.e("MaintenanceActivity", "Error: $it")
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}
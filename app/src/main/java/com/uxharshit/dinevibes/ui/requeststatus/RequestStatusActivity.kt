package com.uxharshit.dinevibes.ui.requeststatus

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.uxharshit.dinevibes.adapter.RequestAdapter
import com.uxharshit.dinevibes.data.models.MaintenanceRequest
import com.uxharshit.dinevibes.data.repository.AuthRepository
import com.uxharshit.dinevibes.data.repository.MaintenanceRepository
import com.uxharshit.dinevibes.databinding.ActivityRequeststatusBinding
import kotlinx.coroutines.launch

class RequestStatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequeststatusBinding

    private var progressDialog: AlertDialog ?= null

    private val requestStatusViewModel: RequestStatusViewModel by viewModels {
        RequestViewModelFactory(MaintenanceRepository(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequeststatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.llToolbar.setOnClickListener {
            finish()
        }
        onBackPressedDispatcher.addCallback(this, object : androidx.activity.OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
        lifecycleScope.launch{
            requestStatusViewModel.getRequestStatus("Bearer ${AuthRepository(applicationContext).getToken()}")
        }


        progressDialog = AlertDialog.Builder(this)
            .setTitle("Loading")
            .setMessage("Please wait...")
            .setCancelable(false)
            .create()


        requestStatusViewModel.isLoading.observe(this) {
            binding.llToolbar.isEnabled = !it
            if (it) {
                progressDialog?.show()
            } else {
                progressDialog?.dismiss()
            }
        }

        requestStatusViewModel.requestResult.observe(this) { result ->
            result.onSuccess { response ->
                Log.d("RequestStatusActivity", "Response: $response")
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                try {
                    val type = object : TypeToken<List<MaintenanceRequest>>() {}.type
                    val list: List<MaintenanceRequest> = gson.fromJson(response, type)

                    val totalResolved = list.count { it.status == "resolved" }
                    val totalPending = list.count { it.status == "pending" }
                    val totalRejected = list.count { it.status == "rejected" }

                    binding.tvApproved.text = totalResolved.toString()
                    binding.tvPending.text = totalPending.toString()
                    binding.tvRejected.text = totalRejected.toString()

                    if(list.isEmpty()){
                        Toast.makeText(this, "No requests found", Toast.LENGTH_SHORT).show()
                    } else {
                        val adapter = RequestAdapter(list)
                        binding.recyclerViewRequests.layoutManager =
                            androidx.recyclerview.widget.LinearLayoutManager(this)
                        binding.recyclerViewRequests.adapter = adapter
                    }
                } catch (e: Exception) {
                    Log.e("RequestStatusActivity", "Error parsing JSON: ${e.message}")
                    Toast.makeText(this, "Error parsing data: ${e.message}", Toast.LENGTH_SHORT).show()
                    return@onSuccess
                }




            }.onFailure {
                Toast.makeText(this, "Something went wrong: ${it.message}", Toast.LENGTH_SHORT).show()
                Log.e("RequestStatusActivity", "Error: ${it.message}")
            }
        }
    }

}
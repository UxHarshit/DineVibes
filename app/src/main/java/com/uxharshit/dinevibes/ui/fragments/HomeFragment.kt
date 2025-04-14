package com.uxharshit.dinevibes.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.uxharshit.dinevibes.databinding.FragmentHomeBinding
import com.uxharshit.dinevibes.ui.maintenance.MaintenanceActivity
import com.uxharshit.dinevibes.ui.requeststatus.RequestStatusActivity

class HomeFragment(private val role: String?) : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.maintainanceButton.setOnClickListener {
            if (role == "Student") {
                Toast.makeText(requireContext(), "You are not allowed to create a request", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(requireContext(), MaintenanceActivity::class.java)
            startActivity(intent)
        }
        binding.maintainanceButtonNav.setOnClickListener {
            if (role == "Student") {
                Toast.makeText(requireContext(), "You are not allowed to create a request", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(requireContext(), RequestStatusActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
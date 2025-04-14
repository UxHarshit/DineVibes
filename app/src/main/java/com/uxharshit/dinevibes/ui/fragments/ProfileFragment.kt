package com.uxharshit.dinevibes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.uxharshit.dinevibes.databinding.FragmentProfileBinding

class ProfileFragment(
    private val email: String?,
    private val name: String?,
    private val phone: String?,
    private val gender: String?,
    private val username: String?,
    private val role : String?
) : Fragment() {


    private var _binding : FragmentProfileBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.emailText.text = email
        binding.nameText.text = name
        binding.genderText.text = gender
        binding.phoneText.text = "+91 $phone"
        binding.roleText.text = role
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
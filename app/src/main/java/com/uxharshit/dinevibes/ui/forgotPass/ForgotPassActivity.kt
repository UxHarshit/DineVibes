package com.uxharshit.dinevibes.ui.forgotPass

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.uxharshit.dinevibes.databinding.ActivityForgotpassBinding

class ForgotPassActivity : AppCompatActivity(){

    private lateinit var binding : ActivityForgotpassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotpassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gobackLogin.setOnClickListener {
            finish()
        }

        binding.sendResetLink.setOnClickListener {
            Toast.makeText(this,"In Progress",Toast.LENGTH_SHORT).show();
        }
    }
}
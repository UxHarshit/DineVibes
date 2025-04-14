package com.uxharshit.dinevibes.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.uxharshit.dinevibes.data.repository.AuthRepository
import com.uxharshit.dinevibes.databinding.ActivitySignupBinding
import com.uxharshit.dinevibes.ui.forgotPass.ForgotPassActivity
import com.uxharshit.dinevibes.ui.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var progressDialog: AlertDialog
    private  lateinit var binding: ActivitySignupBinding

    private val signupViewModel: SignupViewModel by viewModels {
        SignupViewModelFactory(AuthRepository(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.siginText.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        progressDialog = AlertDialog.Builder(this)
            .setTitle("Registering")
            .setMessage("Please wait...")
            .setCancelable(false)
            .create()


        binding.signupButton.setOnClickListener {
            val name = binding.fullNameEdit.text.toString()
            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            val phoneNumber = binding.phoneEdit.text.toString()

            val selectedID = binding.genderRadioGroup.checkedRadioButtonId

            val genderRad = findViewById<RadioButton>(selectedID)
            val gender = genderRad.text

            signupViewModel.register(name, email, password, phoneNumber,gender.toString())
        }
        binding.forgotPasswordText.setOnClickListener {
            val intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent)
        }

        signupViewModel.isLoading.observe(this) {
            binding.signupButton.isEnabled = !it
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        signupViewModel.signupResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
            }.onFailure {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
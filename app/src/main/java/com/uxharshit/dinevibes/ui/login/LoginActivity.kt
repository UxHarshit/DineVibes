package com.uxharshit.dinevibes.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.uxharshit.dinevibes.data.repository.AuthRepository
import com.uxharshit.dinevibes.databinding.ActivityLoginBinding
import com.uxharshit.dinevibes.ui.dashboard.DashboardActivity
import com.uxharshit.dinevibes.ui.forgotPass.ForgotPassActivity
import com.uxharshit.dinevibes.ui.signup.SignupActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    private lateinit var progressDialog: AlertDialog

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(AuthRepository(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val email = binding.emailEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            loginViewModel.login(email, password)
        }

        binding.signupText.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.forgotText.setOnClickListener {
            val intent = Intent(this, ForgotPassActivity::class.java)
            startActivity(intent)
        }

        progressDialog = AlertDialog.Builder(this)
            .setTitle("Logging in")
            .setMessage("Please wait...")
            .setCancelable(false)
            .create()

        loginViewModel.isLoading.observe(this) {
            binding.loginButton.isEnabled = !it
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        }


        val authRepository = AuthRepository(this).getToken()
        if (authRepository != null) {
            loginViewModel.verify(authRepository)
        }

        loginViewModel.loginResult.observe(this) { result ->
            result.onSuccess {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra("name", it.get("name").asString)
                intent.putExtra("email", it.get("email").asString)
                intent.putExtra("username", it.get("username").asString)
                intent.putExtra("phonenumber", it.get("phonenumber").asString)
                intent.putExtra("role",it.get("role").asString)
                intent.putExtra("gender",it.get("gender").asString)
                startActivity(intent)
                finish()
            }.onFailure {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
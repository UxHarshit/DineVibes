package com.uxharshit.dinevibes.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.uxharshit.dinevibes.R
import com.uxharshit.dinevibes.data.repository.AuthRepository
import com.uxharshit.dinevibes.databinding.ActivityDashboardBinding
import com.uxharshit.dinevibes.ui.fragments.HomeFragment
import com.uxharshit.dinevibes.ui.fragments.OrderFragment
import com.uxharshit.dinevibes.ui.fragments.ProfileFragment
import com.uxharshit.dinevibes.ui.login.LoginActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onStart() {
        super.onStart()
        val authRepository = AuthRepository(this).getToken()
        if (authRepository == null) {
            val intent = Intent(this,LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.btmNav


        val email = intent.getStringExtra("email")
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phonenumber")
        val gender = intent.getStringExtra("gender")
        val  username = intent.getStringExtra("username")
        val role = intent.getStringExtra("role")

        loadFragment(HomeFragment(role))


        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment(role))
                    true
                }
                R.id.history -> {
                    loadFragment(OrderFragment())
                    true
                }
                R.id.profile -> {
                    loadFragment(ProfileFragment(email,name,phone,gender,username,role))
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.frameLayout.id, fragment)
        transaction.commit()
    }
}
package com.example.catstagram

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.catstagram.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.fragment_splash)

        Handler(Looper.myLooper()!!).postDelayed({
            setContentView(binding.root)
            val bottomNavView: BottomNavigationView = binding.bottomNavMenu
            val navController = findNavController(R.id.nav_host_fragment)
            bottomNavView.setupWithNavController(navController)
        }, 5000)


    }
}

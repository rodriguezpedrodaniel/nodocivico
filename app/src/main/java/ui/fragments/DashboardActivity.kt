package com.rodriguez.nodocivico

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_dashboard) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        // Ocultar menú de usuarios si no es admin
        val rol = getSharedPreferences("session", Context.MODE_PRIVATE)
            .getString("userRol", "ciudadano")

        if (rol != "admin") {
            bottomNavigation.menu.removeItem(R.id.gestionUsuariosFragment)
        }
    }
}
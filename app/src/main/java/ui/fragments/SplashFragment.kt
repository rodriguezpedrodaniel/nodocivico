package com.rodriguez.nodocivico.ui.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rodriguez.nodocivico.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_splash, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            val token = requireContext()
                .getSharedPreferences("session", Context.MODE_PRIVATE)
                .getString("token", null)

            if (token != null) {
                // Ya hay sesión activa → ir directo al dashboard
                startActivity(
                    android.content.Intent(requireContext(),
                        com.rodriguez.nodocivico.DashboardActivity::class.java).apply {
                        flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                                android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                )
            } else {
                findNavController().navigate(
                    R.id.action_splashFragment_to_loginFragment
                )
            }
        }, 1500)
    }
}
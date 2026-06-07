package com.rodriguez.nodocivico.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rodriguez.nodocivico.DashboardActivity
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.network.RetrofitClient
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private lateinit var tilUsuario: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etUsuario: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tilUsuario  = view.findViewById(R.id.tilUsuario)
        tilPassword = view.findViewById(R.id.tilPassword)
        etUsuario   = view.findViewById(R.id.etUsuario)
        etPassword  = view.findViewById(R.id.etPassword)
        btnLogin    = view.findViewById(R.id.btnLogin)
        progressBar = view.findViewById(R.id.progressBar)

        btnLogin.setOnClickListener { iniciarSesion() }
    }

    private fun iniciarSesion() {
        tilUsuario.error  = null
        tilPassword.error = null

        val correo   = etUsuario.text.toString().trim()
        val password = etPassword.text.toString().trim()

        when {
            correo.isEmpty()          -> { tilUsuario.error = "Ingresa tu correo"; return }
            !correo.contains("@")     -> { tilUsuario.error = "Correo inválido"; return }
            password.isEmpty()        -> { tilPassword.error = "Ingresa tu contraseña"; return }
            password.length < 4      -> { tilPassword.error = "Mínimo 4 caracteres"; return }
        }

        progressBar.visibility = View.VISIBLE
        btnLogin.isEnabled = false

        lifecycleScope.launch {
            try {
                // MockAPI no tiene login real: buscamos todos los usuarios
                // y validamos correo + password en la app
                val response = RetrofitClient.api.obtenerUsuarios()

                if (response.isSuccessful) {
                    val usuarios = response.body() ?: emptyList()
                    val usuario = usuarios.find {
                        it.correo == correo && it.password == password && it.activo
                    }

                    if (usuario != null) {
                        // Guardar sesión
                        requireContext()
                            .getSharedPreferences("session", Context.MODE_PRIVATE)
                            .edit()
                            .putString("token",      usuario.id) // usamos id como token
                            .putString("userId",     usuario.id)
                            .putString("userNombre", usuario.nombre)
                            .putString("userRol",    usuario.rol)
                            .apply()

                        Toast.makeText(requireContext(),
                            "✅ Bienvenido ${usuario.nombre}", Toast.LENGTH_SHORT).show()

                        startActivity(
                            Intent(requireContext(), DashboardActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                        )
                    } else {
                        tilPassword.error = "Correo o contraseña incorrectos"
                    }

                } else {
                    Toast.makeText(requireContext(),
                        "❌ Error del servidor", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(requireContext(),
                    "❌ Sin conexión al servidor", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
                btnLogin.isEnabled = true
            }
        }
    }
}
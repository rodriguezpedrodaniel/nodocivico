package com.rodriguez.nodocivico.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rodriguez.nodocivico.DashboardActivity
import com.rodriguez.nodocivico.R

class LoginFragment : Fragment() {

    private lateinit var tilUsuario: TextInputLayout
    private lateinit var tilPassword: TextInputLayout
    private lateinit var etUsuario: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnLogin: MaterialButton
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // FIX: todos los views se inicializan aquí, antes de cualquier uso
        tilUsuario  = view.findViewById(R.id.tilUsuario)
        tilPassword = view.findViewById(R.id.tilPassword)
        etUsuario   = view.findViewById(R.id.etUsuario)
        etPassword  = view.findViewById(R.id.etPassword)
        btnLogin    = view.findViewById(R.id.btnLogin)
        progressBar = view.findViewById(R.id.progressBar)

        btnLogin.setOnClickListener { iniciarSesion() }
    }

    private fun iniciarSesion() {

        // Limpiar errores previos
        tilUsuario.error = null
        tilPassword.error = null

        val usuario  = etUsuario.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Validaciones
        when {
            usuario.isEmpty() -> {
                tilUsuario.error = "Ingresa tu usuario"
                etUsuario.requestFocus()
                return
            }
            usuario.length < 4 -> {
                tilUsuario.error = "Mínimo 4 caracteres"
                etUsuario.requestFocus()
                return
            }
            password.isEmpty() -> {
                tilPassword.error = "Ingresa tu contraseña"
                etPassword.requestFocus()
                return
            }
            password.length < 4 -> {
                tilPassword.error = "Mínimo 4 caracteres"
                etPassword.requestFocus()
                return
            }
        }

        progressBar.visibility = View.VISIBLE
        btnLogin.isEnabled = false

        if (usuario == "admin" && password == "1234") {
            progressBar.visibility = View.GONE
            btnLogin.isEnabled = true
            Toast.makeText(requireContext(), "✅ Bienvenido $usuario", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), DashboardActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        } else {
            progressBar.visibility = View.GONE
            btnLogin.isEnabled = true
            tilPassword.error = "Usuario o contraseña incorrectos"
        }
    }
}
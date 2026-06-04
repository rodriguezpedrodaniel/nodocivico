package com.rodriguez.nodocivico.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.viewmodel.ReporteViewModel

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ReporteViewModel

    private lateinit var tilNombre:   TextInputLayout
    private lateinit var tilCorreo:   TextInputLayout
    private lateinit var tilTelefono: TextInputLayout
    private lateinit var tilZona:     TextInputLayout

    private lateinit var etNombre:   TextInputEditText
    private lateinit var etCorreo:   TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    private lateinit var etZona:     TextInputEditText

    private lateinit var btnGuardar:  MaterialButton
    private lateinit var progressBar: ProgressBar


    private lateinit var txtTotalReportes: TextView
    private lateinit var txtPendientes:    TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this)[ReporteViewModel::class.java]

        tilNombre   = view.findViewById(R.id.tilNombre)
        tilCorreo   = view.findViewById(R.id.tilCorreo)
        tilTelefono = view.findViewById(R.id.tilTelefono)
        tilZona     = view.findViewById(R.id.tilZona)
        etNombre    = view.findViewById(R.id.etNombre)
        etCorreo    = view.findViewById(R.id.etCorreo)
        etTelefono  = view.findViewById(R.id.etTelefono)
        etZona      = view.findViewById(R.id.etZona)
        btnGuardar  = view.findViewById(R.id.btnGuardarPerfil)
        progressBar = view.findViewById(R.id.progressBar)


        txtTotalReportes = view.findViewById(R.id.txtTotalReportes)
        txtPendientes    = view.findViewById(R.id.txtPendientes)

        cargarDatosDemo()
        observarContadores()

        btnGuardar.setOnClickListener { guardarPerfil() }
    }


    private fun observarContadores() {
        viewModel.totalReportes.observe(viewLifecycleOwner) { total ->
            txtTotalReportes.text = (total ?: 0).toString()
        }
        viewModel.totalPendientes.observe(viewLifecycleOwner) { pendientes ->
            txtPendientes.text = (pendientes ?: 0).toString()
        }
    }

    private fun cargarDatosDemo() {
        etNombre.setText("Pedro Rodriguez")
        etCorreo.setText("pedro@email.com")
        etTelefono.setText("3001234567")
        etZona.setText("Bogotá")
    }

    private fun guardarPerfil() {
        tilNombre.error   = null
        tilCorreo.error   = null
        tilTelefono.error = null
        tilZona.error     = null

        val nombre   = etNombre.text.toString().trim()
        val correo   = etCorreo.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()
        val zona     = etZona.text.toString().trim()

        when {
            nombre.isEmpty() -> {
                tilNombre.error = "Ingresa tu nombre"
                etNombre.requestFocus()
                return
            }
            nombre.length < 3 -> {
                tilNombre.error = "Nombre muy corto"
                etNombre.requestFocus()
                return
            }
            correo.isEmpty() -> {
                tilCorreo.error = "Ingresa tu correo"
                etCorreo.requestFocus()
                return
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() -> {
                tilCorreo.error = "Correo inválido"
                etCorreo.requestFocus()
                return
            }
            telefono.isEmpty() -> {
                tilTelefono.error = "Ingresa teléfono"
                etTelefono.requestFocus()
                return
            }
            telefono.length < 10 -> {
                tilTelefono.error = "Número inválido"
                etTelefono.requestFocus()
                return
            }
            zona.isEmpty() -> {
                tilZona.error = "Ingresa zona"
                etZona.requestFocus()
                return
            }
        }

        progressBar.visibility = View.VISIBLE
        btnGuardar.isEnabled   = false

        Toast.makeText(requireContext(), "✅ Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()

        progressBar.visibility = View.GONE
        btnGuardar.isEnabled   = true
    }
}
package com.rodriguez.nodocivico.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.data.local.entity.Reporte
import com.rodriguez.nodocivico.model.Category
import com.rodriguez.nodocivico.viewmodel.ReporteViewModel

class EditarReporteFragment : Fragment(R.layout.fragment_editar_reporte) {

    private lateinit var viewModel: ReporteViewModel
    private val args: EditarReporteFragmentArgs by navArgs()

    private lateinit var tilTitulo:      TextInputLayout
    private lateinit var tilDescripcion: TextInputLayout
    private lateinit var tilUbicacion:   TextInputLayout
    private lateinit var tilCategoria:   TextInputLayout
    private lateinit var tilEstado:      TextInputLayout

    private lateinit var etTitulo:       TextInputEditText
    private lateinit var etDescripcion:  TextInputEditText
    private lateinit var etUbicacion:    TextInputEditText
    private lateinit var spinnerCategoria: AutoCompleteTextView
    private lateinit var spinnerEstado:  AutoCompleteTextView
    private lateinit var btnActualizar:  MaterialButton
    private lateinit var progressBar:    ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ReporteViewModel::class.java]

        tilTitulo        = view.findViewById(R.id.tilTitulo)
        tilDescripcion   = view.findViewById(R.id.tilDescripcion)
        tilUbicacion     = view.findViewById(R.id.tilUbicacion)
        tilCategoria     = view.findViewById(R.id.tilCategoria)
        tilEstado        = view.findViewById(R.id.tilEstado)
        etTitulo         = view.findViewById(R.id.etTitulo)
        etDescripcion    = view.findViewById(R.id.etDescripcion)
        etUbicacion      = view.findViewById(R.id.etUbicacion)
        spinnerCategoria = view.findViewById(R.id.spinnerCategoria)
        spinnerEstado    = view.findViewById(R.id.spinnerEstado)
        btnActualizar    = view.findViewById(R.id.btnActualizar)
        progressBar      = view.findViewById(R.id.progressBar)

        configurarCategorias()
        configurarEstados()
        cargarDatos()
        observarActualizacion()

        btnActualizar.setOnClickListener { actualizarReporte() }
    }

    private fun configurarCategorias() {
        val categorias = Category.obtenerCategorias().map { "${it.icono} ${it.nombre}" }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            categorias
        )
        spinnerCategoria.setAdapter(adapter)
        spinnerCategoria.setOnClickListener { spinnerCategoria.showDropDown() }
        spinnerCategoria.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) spinnerCategoria.showDropDown()
        }
    }

    private fun configurarEstados() {
        val estados = listOf("Pendiente", "En proceso", "Resuelto")
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            estados
        )
        spinnerEstado.setAdapter(adapter)
        spinnerEstado.setOnClickListener { spinnerEstado.showDropDown() }
        spinnerEstado.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) spinnerEstado.showDropDown()
        }
    }

    private fun cargarDatos() {
        etTitulo.setText(args.titulo)
        etDescripcion.setText(args.descripcion)
        etUbicacion.setText(args.ubicacion)
        spinnerCategoria.setText(args.categoria, false)
        spinnerEstado.setText(args.estado, false)
    }


    private fun observarActualizacion() {
        viewModel.actualizarListo.observe(viewLifecycleOwner) { listo ->
            if (listo == true) {
                progressBar.visibility = View.GONE
                btnActualizar.isEnabled = true
                Toast.makeText(
                    requireContext(),
                    "✅ Reporte actualizado",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun actualizarReporte() {
        tilTitulo.error      = null
        tilDescripcion.error = null
        tilUbicacion.error   = null
        tilCategoria.error   = null
        tilEstado.error      = null

        val titulo      = etTitulo.text.toString().trim()
        val descripcion = etDescripcion.text.toString().trim()
        val ubicacion   = etUbicacion.text.toString().trim()
        val categoria   = spinnerCategoria.text.toString().trim()
        val estado      = spinnerEstado.text.toString().trim()

        when {
            titulo.isEmpty() -> {
                tilTitulo.error = "Ingresa un título"
                etTitulo.requestFocus()
                return
            }
            descripcion.isEmpty() -> {
                tilDescripcion.error = "Ingresa una descripción"
                etDescripcion.requestFocus()
                return
            }
            ubicacion.isEmpty() -> {
                tilUbicacion.error = "Ingresa ubicación"
                etUbicacion.requestFocus()
                return
            }
            categoria.isEmpty() -> {
                tilCategoria.error = "Selecciona categoría"
                spinnerCategoria.requestFocus()
                return
            }
            estado.isEmpty() -> {
                tilEstado.error = "Selecciona estado"
                spinnerEstado.requestFocus()
                return
            }
        }

        progressBar.visibility  = View.VISIBLE
        btnActualizar.isEnabled = false

        val reporteActualizado = Reporte(
            id          = args.id,
            apiId       = args.apiId,
            titulo      = titulo,
            descripcion = descripcion,
            categoria   = categoria,
            ubicacion   = ubicacion,
            estado      = estado
        )

        viewModel.actualizar(reporteActualizado)
    }
}
package com.rodriguez.nodocivico.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.data.local.entity.Reporte
import com.rodriguez.nodocivico.viewmodel.ReporteViewModel

class CrearReporteFragment :
    Fragment(R.layout.fragment_crear_reporte) {

    private lateinit var viewModel: ReporteViewModel

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[
            ReporteViewModel::class.java
        ]

        val etTitulo =
            view.findViewById<EditText>(R.id.etTitulo)

        val etDescripcion =
            view.findViewById<EditText>(R.id.etDescripcion)

        val etUbicacion =
            view.findViewById<EditText>(R.id.etUbicacion)

        val btnGuardar =
            view.findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {

            val titulo =
                etTitulo.text.toString()

            val descripcion =
                etDescripcion.text.toString()

            val ubicacion =
                etUbicacion.text.toString()

            if (
                titulo.isEmpty() ||
                descripcion.isEmpty() ||
                ubicacion.isEmpty()
            ) {

                Toast.makeText(
                    requireContext(),
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                val reporte = Reporte(
                    titulo = titulo,
                    descripcion = descripcion,
                    categoria = ubicacion
                )

                viewModel.insertar(reporte)

                Toast.makeText(
                    requireContext(),
                    "Reporte guardado",
                    Toast.LENGTH_SHORT
                ).show()

                etTitulo.text.clear()
                etDescripcion.text.clear()
                etUbicacion.text.clear()
            }
        }
    }
}
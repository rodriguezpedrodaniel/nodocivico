package com.rodriguez.nodocivico.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.viewmodel.ReporteViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: ReporteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )

        // Botones

        val btnReports =
            view.findViewById<MaterialButton>(R.id.btnReports)

        val btnCreate =
            view.findViewById<MaterialButton>(R.id.btnCreate)

        // TextViews estadísticas

        val txtTotalReportes =
            view.findViewById<TextView>(R.id.txtTotalReportes)

        val txtPendientes =
            view.findViewById<TextView>(R.id.txtPendientes)

        // ViewModel

        viewModel = ViewModelProvider(this)[
            ReporteViewModel::class.java
        ]

        // Mostrar total reportes

        viewModel.totalReportes.observe(
            viewLifecycleOwner
        ) { total ->

            txtTotalReportes.text =
                total.toString()
        }

        // Mostrar pendientes

        viewModel.totalPendientes.observe(
            viewLifecycleOwner
        ) { pendientes ->

            txtPendientes.text =
                pendientes.toString()
        }

        // Navegación

        btnReports.setOnClickListener {

            findNavController().navigate(
                R.id.action_homeFragment_to_reportListFragment
            )
        }

        btnCreate.setOnClickListener {

            findNavController().navigate(
                R.id.action_homeFragment_to_createReportFragment
            )
        }

        return view
    }
}
package com.rodriguez.nodocivico.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
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

        val btnSync =
            view.findViewById<MaterialButton>(R.id.btnSync)

        // TextViews

        val txtTotalReportes =
            view.findViewById<TextView>(R.id.txtTotalReportes)

        val txtPendientes =
            view.findViewById<TextView>(R.id.txtPendientes)

        val txtConexion =
            view.findViewById<TextView>(R.id.txtConexion)

        // ViewModel

        viewModel = ViewModelProvider(this)[
            ReporteViewModel::class.java
        ]

        // Mostrar total de reportes

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

        // Estado de conexión

        if (hayInternet()) {

            txtConexion.text =
                "Conectado a internet"

        } else {

            txtConexion.text =
                "Sin conexión"
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
        btnSync.setOnClickListener {

            if (hayInternet()) {

                viewModel.sincronizar()

                txtConexion.text =
                    "Reportes sincronizados"

            } else {

                txtConexion.text =
                    "Sin conexión a internet"
            }
        }

        return view
    }

    private fun hayInternet(): Boolean {

        val connectivityManager =
            requireContext().getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

        val network =
            connectivityManager.activeNetwork
                ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(network)
                ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        )
    }
}
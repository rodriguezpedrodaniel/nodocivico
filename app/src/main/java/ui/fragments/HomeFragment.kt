package com.rodriguez.nodocivico.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.viewmodel.ReporteViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: ReporteViewModel

    private lateinit var btnReports: MaterialButton
    private lateinit var btnCreate: MaterialButton
    private lateinit var btnSync: MaterialButton
    private lateinit var txtTotalReportes: TextView
    private lateinit var txtPendientes: TextView
    private lateinit var txtConexion: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ReporteViewModel::class.java]

        initViews(view)
        observarDatos()
        actualizarEstadoConexion()
        configurarBotones()
    }

    private fun initViews(view: View) {
        btnReports       = view.findViewById(R.id.btnReports)
        btnCreate        = view.findViewById(R.id.btnCreate)
        btnSync          = view.findViewById(R.id.btnSync)
        txtTotalReportes = view.findViewById(R.id.txtTotalReportes)
        txtPendientes    = view.findViewById(R.id.txtPendientes)
        txtConexion      = view.findViewById(R.id.txtConexion)
    }

    private fun observarDatos() {
        viewModel.totalReportes.observe(viewLifecycleOwner) { total ->
            txtTotalReportes.text = total.toString()
        }
        viewModel.totalPendientes.observe(viewLifecycleOwner) { pendientes ->
            txtPendientes.text = pendientes.toString()
        }

        viewModel.sincronizando.observe(viewLifecycleOwner) { sincronizando ->
            if (sincronizando) {
                btnSync.isEnabled = false
                txtConexion.text = "🔄 Sincronizando..."
            } else {
                btnSync.isEnabled = true
                txtConexion.text = if (hayInternet()) "🌐 Conectado" else "📡 Sin conexión"
            }
        }
    }

    private fun configurarBotones() {
        btnReports.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_reportListFragment)
        }
        btnCreate.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createReportFragment)
        }
        btnSync.setOnClickListener {
            sincronizar()
        }
    }

    private fun sincronizar() {
        if (hayInternet()) {

            viewModel.sincronizar()
            Toast.makeText(requireContext(), "✅ Reportes sincronizados", Toast.LENGTH_SHORT).show()
        } else {
            txtConexion.text = "📡 Sin conexión"
            Toast.makeText(requireContext(), "Sin internet para sincronizar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun actualizarEstadoConexion() {
        txtConexion.text = if (hayInternet()) "🌐 Conectado" else "📡 Sin conexión"
    }

    private fun hayInternet(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                } else {
                    @Suppress("DEPRECATION")
                    val networkInfo = connectivityManager.activeNetworkInfo
                    @Suppress("DEPRECATION")
                    networkInfo != null && networkInfo.isConnected
                }
    }
}
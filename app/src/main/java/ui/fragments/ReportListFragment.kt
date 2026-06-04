package com.rodriguez.nodocivico.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.adapter.ReportAdapter
import com.rodriguez.nodocivico.data.local.entity.Reporte
import com.rodriguez.nodocivico.viewmodel.ReporteViewModel

class ReportListFragment : Fragment(R.layout.fragment_report_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReportAdapter
    private lateinit var viewModel: ReporteViewModel
    private lateinit var txtEmpty: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ReporteViewModel::class.java]

        recyclerView = view.findViewById(R.id.recyclerReports)
        txtEmpty     = view.findViewById(R.id.txtEmpty)

        configurarRecycler()
        observarReportes()
    }

    private fun configurarRecycler() {
        adapter = ReportAdapter(
            emptyList(),
            onEditClick   = { reporte -> editarReporte(reporte) },
            onDeleteClick = { reporte -> confirmarEliminar(reporte) }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun observarReportes() {
        viewModel.todosLosReportes.observe(viewLifecycleOwner) { lista ->
            adapter.actualizarLista(lista)
            if (lista.isEmpty()) {
                txtEmpty.visibility     = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                txtEmpty.visibility     = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun confirmarEliminar(reporte: Reporte) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar reporte")
            .setMessage("¿Deseas eliminar \"${reporte.titulo}\"?")
            .setPositiveButton("Eliminar") { _, _ -> eliminarReporte(reporte) }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun eliminarReporte(reporte: Reporte) {
        viewModel.eliminar(reporte)
        Toast.makeText(requireContext(), "🗑️ Reporte eliminado", Toast.LENGTH_SHORT).show()
    }

    private fun editarReporte(reporte: Reporte) {

        val action = ReportListFragmentDirections
            .actionReportListFragmentToEditarReporteFragment(
                id          = reporte.id,
                apiId       = reporte.apiId,
                titulo      = reporte.titulo,
                descripcion = reporte.descripcion,
                ubicacion   = reporte.ubicacion,
                categoria   = reporte.categoria,
                estado      = reporte.estado
            )
        findNavController().navigate(action)
    }
}
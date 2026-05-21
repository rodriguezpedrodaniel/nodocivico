package com.rodriguez.nodocivico.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.adapter.ReportAdapter
import com.rodriguez.nodocivico.viewmodel.ReporteViewModel

class ReportListFragment :
    Fragment(R.layout.fragment_report_list) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ReportAdapter
    private lateinit var viewModel: ReporteViewModel
    private lateinit var txtEmpty: TextView

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        recyclerView =
            view.findViewById(R.id.recyclerReports)

        txtEmpty =
            view.findViewById(R.id.txtEmpty)

        adapter = ReportAdapter(emptyList()) { reporte ->

            viewModel.eliminar(reporte)
        }

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[
            ReporteViewModel::class.java
        ]

        viewModel.todosLosReportes.observe(
            viewLifecycleOwner
        ) { lista ->

            adapter.setData(lista)

            if (lista.isEmpty()) {

                txtEmpty.visibility = View.VISIBLE

            } else {

                txtEmpty.visibility = View.GONE
            }
        }
    }
}
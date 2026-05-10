package com.rodriguez.nodocivico.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rodriguez.nodocivico.R

class HomeFragment : Fragment() {

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

        val btnReports = view.findViewById<Button>(R.id.btnReports)
        val btnCreate = view.findViewById<Button>(R.id.btnCreate)

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
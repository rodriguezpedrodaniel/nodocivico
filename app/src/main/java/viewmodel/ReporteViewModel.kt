package com.rodriguez.nodocivico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rodriguez.nodocivico.data.local.database.AppDatabase
import com.rodriguez.nodocivico.data.local.entity.Reporte
import com.rodriguez.nodocivico.repository.ReporteRepository
import kotlinx.coroutines.launch

class ReporteViewModel(application: Application)
    : AndroidViewModel(application) {

    private val repository: ReporteRepository

    val todosLosReportes: LiveData<List<Reporte>>

    val totalReportes: LiveData<Int>

    val totalPendientes: LiveData<Int>

    init {

        val dao = AppDatabase
            .getDatabase(application)
            .reporteDao()

        repository = ReporteRepository(dao)

        todosLosReportes = repository.todosLosReportes

        totalReportes = repository.totalReportes

        totalPendientes = repository.totalPendientes
    }

    fun insertar(reporte: Reporte) =
        viewModelScope.launch {

            repository.insertar(reporte)
        }

    fun eliminar(reporte: Reporte) =
        viewModelScope.launch {

            repository.eliminar(reporte)
        }
}
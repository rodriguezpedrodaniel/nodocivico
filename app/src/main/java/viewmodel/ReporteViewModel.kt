package com.rodriguez.nodocivico.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rodriguez.nodocivico.data.local.database.AppDatabase
import com.rodriguez.nodocivico.data.local.entity.Reporte
import com.rodriguez.nodocivico.repository.ReporteRepository
import kotlinx.coroutines.launch

class ReporteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ReporteRepository

    val todosLosReportes: LiveData<List<Reporte>>
    val totalReportes:    LiveData<Int>
    val totalPendientes:  LiveData<Int>
    val totalResueltos:   LiveData<Int>

    private val _sincronizando = MutableLiveData<Boolean>(false)
    val sincronizando: LiveData<Boolean> = _sincronizando

    private val _insertarListo = MutableLiveData<Boolean>()
    val insertarListo: LiveData<Boolean> = _insertarListo

    private val _actualizarListo = MutableLiveData<Boolean>()
    val actualizarListo: LiveData<Boolean> = _actualizarListo

    init {
        val dao = AppDatabase.getDatabase(application).reporteDao()
        repository = ReporteRepository(dao)

        todosLosReportes = repository.todosLosReportes
        totalReportes    = repository.totalReportes
        totalPendientes  = repository.totalPendientes
        totalResueltos   = repository.totalResueltos
    }


    fun insertar(reporte: Reporte) = viewModelScope.launch {
        repository.insertar(reporte)
        _insertarListo.postValue(true)
    }

    fun actualizar(reporte: Reporte) = viewModelScope.launch {
        repository.actualizar(reporte)
        _actualizarListo.postValue(true)
    }

    fun eliminar(reporte: Reporte) = viewModelScope.launch {
        repository.eliminar(reporte)
    }

    fun sincronizar() = viewModelScope.launch {
        _sincronizando.postValue(true)
        repository.sincronizarReportes()
        _sincronizando.postValue(false)
    }
}
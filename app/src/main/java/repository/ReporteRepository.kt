package com.rodriguez.nodocivico.repository

import androidx.lifecycle.LiveData
import com.rodriguez.nodocivico.data.local.dao.ReporteDao
import com.rodriguez.nodocivico.data.local.entity.Reporte

class ReporteRepository(
    private val reporteDao: ReporteDao
) {

    val todosLosReportes: LiveData<List<Reporte>> =
        reporteDao.obtenerReportes()
    val totalReportes = reporteDao.contarReportes()

    val totalPendientes = reporteDao.contarPendientes()

    suspend fun insertar(reporte: Reporte) {
        reporteDao.insertarReporte(reporte)
    }

    suspend fun actualizar(reporte: Reporte) {
        reporteDao.actualizarReporte(reporte)
    }

    suspend fun eliminar(reporte: Reporte) {
        reporteDao.eliminarReporte(reporte)
    }

}
package com.rodriguez.nodocivico.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.rodriguez.nodocivico.data.local.dao.ReporteDao
import com.rodriguez.nodocivico.data.local.entity.Reporte
import com.rodriguez.nodocivico.model.ReporteRequest
import com.rodriguez.nodocivico.network.RetrofitClient

class ReporteRepository(private val reporteDao: ReporteDao) {


    val todosLosReportes: LiveData<List<Reporte>> = reporteDao.obtenerReportes()
    val totalReportes: LiveData<Int>              = reporteDao.contarReportes()
    val totalPendientes: LiveData<Int>            = reporteDao.contarPendientes()
    val totalResueltos: LiveData<Int>             = reporteDao.contarResueltos()



    suspend fun insertar(reporte: Reporte) {
        try {
            val request = ReporteRequest(
                titulo      = reporte.titulo,
                descripcion = reporte.descripcion,
                categoria   = reporte.categoria,
                ubicacion   = reporte.ubicacion,
                estado      = reporte.estado,
                fecha       = reporte.fecha,
                sincronizado = true
            )

            val response = RetrofitClient.api.crearReporte(request)

            if (response.isSuccessful) {
                val apiReporte = response.body()
                val reporteConApiId = reporte.copy(
                    apiId        = apiReporte?.id ?: "",
                    sincronizado = true
                )
                reporteDao.insertarReporte(reporteConApiId)
                Log.d("API_REAL", "✅ Reporte enviado correctamente")
            } else {
                guardarOffline(reporte)
                Log.e("API_REAL", "❌ Error servidor: ${response.code()}")
            }
        } catch (e: Exception) {
            guardarOffline(reporte)
            Log.e("API_REAL", "❌ Error internet: ${e.message}")
        }
    }


    private suspend fun guardarOffline(reporte: Reporte) {
        reporteDao.insertarReporte(reporte.copy(sincronizado = false))
    }

    // =========================
    // SINCRONIZAR
    // =========================

    suspend fun sincronizarReportes() {
        try {
            val response = RetrofitClient.api.obtenerReportes()

            if (response.isSuccessful) {
                response.body()?.forEach { apiReporte ->
                    val existe = reporteDao.existeReporte(apiReporte.id)
                    if (!existe) {
                        val reporte = Reporte(
                            apiId       = apiReporte.id,
                            titulo      = apiReporte.titulo,
                            descripcion = apiReporte.descripcion,
                            categoria   = apiReporte.categoria,
                            ubicacion   = apiReporte.ubicacion,
                            estado      = apiReporte.estado,
                            fecha       = apiReporte.fecha,
                            sincronizado = true
                        )
                        reporteDao.insertarReporte(reporte)
                    }
                }
                Log.d("API_REAL", "✅ Sincronización completa")
            }
        } catch (e: Exception) {
            Log.e("API_REAL", "❌ Error sincronizando: ${e.message}")
        }
    }



    suspend fun actualizar(reporte: Reporte) {
        try {
            if (reporte.apiId.isNotEmpty()) {
                val request = ReporteRequest(
                    id          = reporte.apiId,
                    titulo      = reporte.titulo,
                    descripcion = reporte.descripcion,
                    categoria   = reporte.categoria,
                    ubicacion   = reporte.ubicacion,
                    estado      = reporte.estado,
                    fecha       = reporte.fecha,
                    sincronizado = true
                )


                val response = RetrofitClient.api.actualizarReporte(reporte.apiId, request)
                    if (response.isSuccessful) {
                        reporteDao.actualizarReporte(reporte.copy(sincronizado = true))
                        Log.d("API_REAL", "✅ Reporte actualizado")
                    } else {
                        reporteDao.actualizarReporte(reporte.copy(sincronizado = false))
                        Log.e("API_REAL", "❌ Error actualizando: ${response.code()}")
                    }
            } else {
                reporteDao.actualizarReporte(reporte.copy(sincronizado = false))
            }
        } catch (e: Exception) {
            reporteDao.actualizarReporte(reporte.copy(sincronizado = false))
            Log.e("API_REAL", "❌ Error actualizando: ${e.message}")
        }
    }


    suspend fun eliminar(reporte: Reporte) {
        try {
                if (reporte.apiId.isNotEmpty()) {
                    val response = RetrofitClient.api.eliminarReporte(reporte.apiId)
                    if (response.isSuccessful) {
                        reporteDao.eliminarReporte(reporte)
                        Log.d("API_REAL", "✅ Reporte eliminado")
                    } else {
                        Log.e("API_REAL", "❌ Error eliminando en API: ${response.code()}")
                    }
                } else {
                    // Sin apiId: solo existe localmente, se puede borrar directo
                    reporteDao.eliminarReporte(reporte)
                    Log.d("API_REAL", "✅ Reporte local eliminado")
                }
        } catch (e: Exception) {
            // Sin conexión: eliminar localmente igual para no bloquear al usuario
            reporteDao.eliminarReporte(reporte)
            Log.e("API_REAL", "❌ Error eliminando (offline): ${e.message}")
        }
    }


    fun buscarReportes(texto: String): LiveData<List<Reporte>> {
        return reporteDao.buscarReportes(texto)
    }



    fun obtenerPorEstado(estado: String): LiveData<List<Reporte>> {
        return reporteDao.obtenerPorEstado(estado)
    }
}
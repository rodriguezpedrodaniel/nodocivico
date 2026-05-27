package com.rodriguez.nodocivico.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.rodriguez.nodocivico.data.local.dao.ReporteDao
import com.rodriguez.nodocivico.data.local.entity.Reporte
import com.rodriguez.nodocivico.model.ReporteRequest
import com.rodriguez.nodocivico.network.RetrofitClient

class ReporteRepository(
    private val reporteDao: ReporteDao
) {

    val todosLosReportes: LiveData<List<Reporte>> =
        reporteDao.obtenerReportes()

    val totalReportes =
        reporteDao.contarReportes()

    val totalPendientes =
        reporteDao.contarPendientes()

    suspend fun insertar(reporte: Reporte) {

        try {

            val request = ReporteRequest(

                titulo = reporte.titulo,

                descripcion = reporte.descripcion,

                categoria = reporte.categoria,

                estado = reporte.estado
            )

            val response =
                RetrofitClient.api.crearReporte(request)

            if (response.isSuccessful) {

                val apiReporte = response.body()

                val reporteConApiId = reporte.copy(
                    apiId = apiReporte?.id ?: ""
                )

                reporteDao.insertarReporte(
                    reporteConApiId
                )

                Log.d(
                    "API_REAL",
                    "Reporte enviado correctamente"
                )

            } else {

                reporteDao.insertarReporte(reporte)

                Log.e(
                    "API_REAL",
                    "Error servidor"
                )
            }

        } catch (e: Exception) {

            reporteDao.insertarReporte(reporte)

            Log.e(
                "API_REAL",
                "Error internet: ${e.message}"
            )
        }
    }

    suspend fun sincronizarReportes() {

        try {

            val response =
                RetrofitClient.api.obtenerReportes()

            if (response.isSuccessful) {

                val reportesApi = response.body()

                reportesApi?.forEach { apiReporte ->

                    val existe =
                        reporteDao.existeReporte(
                            apiReporte.titulo,
                            apiReporte.descripcion
                        )

                    if (!existe) {

                        val reporte = Reporte(

                            apiId = apiReporte.id,

                            titulo = apiReporte.titulo,

                            descripcion = apiReporte.descripcion,

                            categoria = apiReporte.categoria,

                            estado = apiReporte.estado
                        )

                        reporteDao.insertarReporte(reporte)
                    }
                }
            }

        } catch (e: Exception) {

            Log.e(
                "API_REAL",
                "Error sincronizando: ${e.message}"
            )
        }
    }

    suspend fun actualizar(reporte: Reporte) {

        reporteDao.actualizarReporte(reporte)
    }

    suspend fun eliminar(reporte: Reporte) {

        reporteDao.eliminarReporte(reporte)

        try {

            if (reporte.apiId.isNotEmpty()) {

                RetrofitClient.api.eliminarReporte(
                    reporte.apiId
                )
            }

        } catch (e: Exception) {

            Log.e(
                "API_REAL",
                "Error eliminando: ${e.message}"
            )
        }
    }
}
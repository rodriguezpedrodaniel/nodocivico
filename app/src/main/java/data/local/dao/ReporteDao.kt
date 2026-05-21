package com.rodriguez.nodocivico.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rodriguez.nodocivico.data.local.entity.Reporte

@Dao
interface ReporteDao {

    @Query("SELECT * FROM reportes")
    fun obtenerReportes(): LiveData<List<Reporte>>

    @Insert
    suspend fun insertarReporte(reporte: Reporte)

    @Update
    suspend fun actualizarReporte(reporte: Reporte)

    @Delete
    suspend fun eliminarReporte(reporte: Reporte)

    @Query("SELECT COUNT(*) FROM reportes")
    fun contarReportes(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM reportes WHERE estado = 'Pendiente'")
    fun contarPendientes(): LiveData<Int>
}
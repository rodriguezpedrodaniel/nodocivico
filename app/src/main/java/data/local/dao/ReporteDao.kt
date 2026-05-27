package com.rodriguez.nodocivico.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rodriguez.nodocivico.data.local.entity.Reporte

@Dao
interface ReporteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarReporte(reporte: Reporte)

    @Query("SELECT * FROM reportes ORDER BY id DESC")
    fun obtenerReportes(): LiveData<List<Reporte>>

    @Query("SELECT COUNT(*) FROM reportes")
    fun contarReportes(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM reportes WHERE estado = 'Pendiente'")
    fun contarPendientes(): LiveData<Int>

    @Update
    suspend fun actualizarReporte(reporte: Reporte)

    @Delete
    suspend fun eliminarReporte(reporte: Reporte)

    @Query("DELETE FROM reportes")
    suspend fun eliminarTodos()

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM reportes
            WHERE titulo = :titulo
            AND descripcion = :descripcion
        )
    """)
    suspend fun existeReporte(
        titulo: String,
        descripcion: String
    ): Boolean
}
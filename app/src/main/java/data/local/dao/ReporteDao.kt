package com.rodriguez.nodocivico.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rodriguez.nodocivico.data.local.entity.Reporte

@Dao
interface ReporteDao {


    @Query("SELECT * FROM reportes ORDER BY id DESC")
    fun obtenerReportes(): LiveData<List<Reporte>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarReporte(
        reporte: Reporte
    )



    @Update
    suspend fun actualizarReporte(
        reporte: Reporte
    )


    @Delete
    suspend fun eliminarReporte(
        reporte: Reporte
    )



    @Query("SELECT COUNT(*) FROM reportes")
    fun contarReportes(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM reportes WHERE estado = 'Pendiente'")
    fun contarPendientes(): LiveData<Int>

    @Query("SELECT COUNT(*) FROM reportes WHERE estado = 'Resuelto'")
    fun contarResueltos(): LiveData<Int>


    @Query("""
        SELECT * FROM reportes
        WHERE titulo LIKE '%' || :texto || '%'
        OR descripcion LIKE '%' || :texto || '%'
        ORDER BY id DESC
    """)
    fun buscarReportes(
        texto: String
    ): LiveData<List<Reporte>>


    @Query("""
        SELECT * FROM reportes
        WHERE estado = :estado
        ORDER BY id DESC
    """)
    fun obtenerPorEstado(
        estado: String
    ): LiveData<List<Reporte>>



    @Query("""
        SELECT EXISTS(
            SELECT 1
            FROM reportes
            WHERE apiId = :apiId
        )
    """)
    suspend fun existeReporte(
        apiId: String
    ): Boolean
}
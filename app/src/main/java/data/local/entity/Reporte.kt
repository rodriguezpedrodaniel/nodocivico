package com.rodriguez.nodocivico.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reportes")
data class Reporte(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val titulo: String,

    val descripcion: String,

    val categoria: String,

    val estado: String = "Pendiente"
)
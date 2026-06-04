package com.rodriguez.nodocivico.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reportes")
data class Reporte(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,


    val apiId: String = "",

    val titulo: String,

    val descripcion: String,

    val categoria: String,

    val ubicacion: String,

    val estado: String = "Pendiente",

    // Fecha de creación
    val fecha: Long = System.currentTimeMillis(),

    // Saber si ya fue sincronizado
    val sincronizado: Boolean = false
)
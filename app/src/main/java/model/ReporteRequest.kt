package com.rodriguez.nodocivico.model

data class ReporteRequest(

    val id: String? = null,

    val titulo: String,

    val descripcion: String,

    val categoria: String,

    val ubicacion: String,

    val estado: String = "Pendiente",

    val fecha: Long = System.currentTimeMillis(),

    val sincronizado: Boolean = false
)
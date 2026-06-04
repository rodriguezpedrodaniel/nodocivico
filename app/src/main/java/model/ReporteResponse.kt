package com.rodriguez.nodocivico.model

data class ReporteResponse(

    val id: String,

    val titulo: String,

    val descripcion: String,

    val categoria: String,

    val ubicacion: String,

    val estado: String,

    val fecha: Long,

    val sincronizado: Boolean
)
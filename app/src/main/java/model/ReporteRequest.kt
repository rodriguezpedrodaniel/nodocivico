package com.rodriguez.nodocivico.model

data class ReporteRequest(

    val id: String? = null,

    val titulo: String,

    val descripcion: String,

    val categoria: String,

    val estado: String
)
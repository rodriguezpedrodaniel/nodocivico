package com.rodriguez.nodocivico.model

data class User(

    val id: Int = 0,

    val nombre: String,

    val correo: String,

    val telefono: String = "",

    val rol: String = "Ciudadano",

    val activo: Boolean = true
)
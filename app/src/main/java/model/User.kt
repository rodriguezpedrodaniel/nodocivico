package com.rodriguez.nodocivico.model

data class User(
    val id: String = "",
    val nombre: String = "",
    val correo: String = "",
    val password: String = "",
    val telefono: String = "",
    val rol: String = "ciudadano",
    val activo: Boolean = true
)
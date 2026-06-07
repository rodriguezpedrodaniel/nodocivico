package com.rodriguez.nodocivico.model

data class LoginResponse(
    val token: String,
    val user: User
)
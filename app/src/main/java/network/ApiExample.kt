package com.rodriguez.nodocivico.network

object ApiExample {

    // JSON esperado para login

    val loginResponse = """
        {
          "userId": 1,
          "name": "Pedro Rodriguez",
          "email": "pedro@email.com",
          "token": "abc123token"
        }
    """.trimIndent()

    // JSON esperado para reportes

    val reportsResponse = """
        [
          {
            "id": 1,
            "title": "Basura en parque",
            "description": "Hay basura acumulada",
            "category": "Limpieza",
            "status": "Pendiente"
          },
          {
            "id": 2,
            "title": "Luz dañada",
            "description": "Farola apagada",
            "category": "Iluminación",
            "status": "En proceso"
          }
        ]
    """.trimIndent()
}
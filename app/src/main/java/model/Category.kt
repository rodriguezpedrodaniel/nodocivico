package com.rodriguez.nodocivico.model

data class Category(

    val id: Int,

    val nombre: String,

    val icono: String
) {

    companion object {

        fun obtenerCategorias(): List<Category> {

            return listOf(

                Category(
                    1,
                    "Seguridad",
                    "🛡️"
                ),

                Category(
                    2,
                    "Infraestructura",
                    "🏗️"
                ),

                Category(
                    3,
                    "Alumbrado",
                    "💡"
                ),

                Category(
                    4,
                    "Basura",
                    "🗑️"
                ),

                Category(
                    5,
                    "Tránsito",
                    "🚦"
                ),

                Category(
                    6,
                    "Agua",
                    "💧"
                ),

                Category(
                    7,
                    "Ruido",
                    "🔊"
                ),

                Category(
                    8,
                    "Otro",
                    "📌"
                )
            )
        }
    }
}
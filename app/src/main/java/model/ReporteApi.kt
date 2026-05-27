package com.rodriguez.nodocivico.network

import com.rodriguez.nodocivico.model.ReporteRequest
import retrofit2.Response
import retrofit2.http.*

interface ReporteApi {

    @POST("posts")
    suspend fun crearReporte(
        @Body reporte: ReporteRequest
    ): Response<ReporteRequest>

    @GET("posts")
    suspend fun obtenerReportes():
            Response<List<ReporteRequest>>

    @DELETE("posts/{id}")
    suspend fun eliminarReporte(
        @Path("id") id: String
    ): Response<Unit>
}
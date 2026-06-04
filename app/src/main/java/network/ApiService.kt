package com.rodriguez.nodocivico.network

import com.rodriguez.nodocivico.model.ReporteRequest
import com.rodriguez.nodocivico.model.ReporteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("reportes")
    suspend fun crearReporte(

        @Body reporte: ReporteRequest

    ): Response<ReporteResponse>


    @GET("reportes")
    suspend fun obtenerReportes():
            Response<List<ReporteResponse>>


    @GET("reportes/{id}")
    suspend fun obtenerReportePorId(

        @Path("id") id: String

    ): Response<ReporteResponse>


    @PUT("reportes/{id}")
    suspend fun actualizarReporte(

        @Path("id") id: String,

        @Body reporte: ReporteRequest

    ): Response<ReporteResponse>


    @DELETE("reportes/{id}")
    suspend fun eliminarReporte(

        @Path("id") id: String

    ): Response<Unit>
}
package com.rodriguez.nodocivico.network

import com.rodriguez.nodocivico.model.ReporteRequest
import com.rodriguez.nodocivico.model.ReporteResponse
import com.rodriguez.nodocivico.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // ── USUARIOS ───

    @GET("usuarios")
    suspend fun obtenerUsuarios(): Response<List<User>>

    @GET("usuarios?correo={correo}")
    suspend fun buscarUsuarioPorCorreo(
        @Path("correo") correo: String
    ): Response<List<User>>

    @POST("usuarios")
    suspend fun crearUsuario(
        @Body user: User
    ): Response<User>

    @PUT("usuarios/{id}")
    suspend fun actualizarUsuario(
        @Path("id") id: String,
        @Body user: User
    ): Response<User>

    @DELETE("usuarios/{id}")
    suspend fun eliminarUsuario(
        @Path("id") id: String
    ): Response<Unit>

    // ── REPORTES ──

    @POST("reportes")
    suspend fun crearReporte(
        @Body reporte: ReporteRequest
    ): Response<ReporteResponse>

    @GET("reportes")
    suspend fun obtenerReportes(): Response<List<ReporteResponse>>

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
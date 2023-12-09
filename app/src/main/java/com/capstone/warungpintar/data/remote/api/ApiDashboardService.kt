package com.capstone.warungpintar.data.remote.api

import com.capstone.warungpintar.data.remote.model.response.DashboardResponse
import com.capstone.warungpintar.data.remote.model.response.ResponseAPI
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDashboardService {

    @GET("api/dashboard/{email}")
    suspend fun getDashboard(@Path("email") email: String): ResponseAPI<DashboardResponse>
}
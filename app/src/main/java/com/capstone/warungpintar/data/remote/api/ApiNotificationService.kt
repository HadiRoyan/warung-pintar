package com.capstone.warungpintar.data.remote.api

import com.capstone.warungpintar.data.remote.model.response.NotificationResponse
import com.capstone.warungpintar.data.remote.model.response.ResponseAPI
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiNotificationService {

    @GET("api/notification/{email}")
    suspend fun getListHistory(@Path("email") email: String): ResponseAPI<List<NotificationResponse>>
}
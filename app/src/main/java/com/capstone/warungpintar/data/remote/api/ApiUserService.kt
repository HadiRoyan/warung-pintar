package com.capstone.warungpintar.data.remote.api

import com.capstone.warungpintar.data.remote.model.request.RegisterRequest
import com.capstone.warungpintar.data.remote.model.response.LoginResponse
import com.capstone.warungpintar.data.remote.model.response.ResponseAPI
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface ApiUserService {

    @POST("api/auth/register")
    suspend fun postRegister(
        @Body bodyRegister: RegisterRequest
    ): ResponseAPI<String>


    @POST("api/auth/login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseAPI<LoginResponse>
}
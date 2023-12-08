package com.capstone.warungpintar.data.remote.api

import com.capstone.warungpintar.data.model.User
import com.capstone.warungpintar.data.remote.model.request.RegisterRequest
import com.capstone.warungpintar.data.remote.model.response.LoginResponse
import com.capstone.warungpintar.data.remote.model.response.ResponseAPI
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiUserService {

    @POST("api/auth/register")
    suspend fun postRegister(
        @Body bodyRegister: RegisterRequest
    ): ResponseAPI<String>


    @POST("api/auth/login")
    @FormUrlEncoded
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseAPI<LoginResponse>

    @GET("api/users/{email}")
    suspend fun getUserDetail(
        @Path("email") email: String
    ): ResponseAPI<User>
}
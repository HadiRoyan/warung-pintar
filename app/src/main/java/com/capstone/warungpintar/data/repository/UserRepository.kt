package com.capstone.warungpintar.data.repository

import android.util.Log
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.api.ApiUserService
import com.capstone.warungpintar.data.remote.model.response.ErrorResponse
import com.capstone.warungpintar.data.remote.model.response.LoginResponse
import com.capstone.warungpintar.data.remote.model.response.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException

class UserRepository(
    private val apiUserService: ApiUserService
) {
    companion object {
        private const val TAG = "UserRepository"

        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(apiUserService: ApiUserService) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiUserService)
            }.also { instance = it }
    }

    fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)

        try {
            val response: RegisterResponse = apiUserService.postRegister(name, email, password)
            emit(ResultState.Success(response.message))
        } catch (e: HttpException) {
            val errorMessage: String = if (e.code() >= 500) {
                "A server error occurred, try again later"
            } else {
                val jsonString = e.response()?.errorBody()?.string()
                val error: ErrorResponse = Gson().fromJson(jsonString, ErrorResponse::class.java)
                error.message
            }
            emit(ResultState.Error(errorMessage))
            Log.d(TAG, "register error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "register error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "register error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

    fun login(email: String, password: String): Flow<ResultState<LoginResponse>> = flow {
        emit(ResultState.Loading)

        try {
            val response: LoginResponse = apiUserService.postLogin(email, password)
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val errorMessage: String = if (e.code() >= 500) {
                "A server error occurred, try again later"
            } else {
                val jsonString = e.response()?.errorBody()?.string()
                val error: ErrorResponse = Gson().fromJson(jsonString, ErrorResponse::class.java)
                error.message
            }
            emit(ResultState.Error(errorMessage))
            Log.d(TAG, "login error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "login error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "login error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

}
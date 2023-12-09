package com.capstone.warungpintar.data.repository

import android.util.Log
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.api.ApiNotificationService
import com.capstone.warungpintar.data.remote.model.response.ErrorResponse
import com.capstone.warungpintar.data.remote.model.response.NotificationResponse
import com.capstone.warungpintar.data.remote.model.response.ResponseAPI
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException

class NotificationRepository(
    private val apiNotificationService: ApiNotificationService
) {

    companion object {
        private const val TAG = "NotificationRepository"

        @Volatile
        private var instance: NotificationRepository? = null
        fun getInstance(apiNotificationService: ApiNotificationService) =
            instance ?: synchronized(this) {
                instance ?: NotificationRepository(apiNotificationService)
            }.also { instance = it }
    }

    fun getListNotification(email: String): Flow<ResultState<List<NotificationResponse>>> = flow {
        emit(ResultState.Loading)

        try {
            val response: ResponseAPI<List<NotificationResponse>> =
                apiNotificationService.getListHistory(email)
            emit(ResultState.Success(response.data))
        } catch (e: HttpException) {
            val errorMessage: String = if (e.code() >= 500) {
                "A server error occurred, try again later"
            } else {
                val jsonString = e.response()?.errorBody()?.string()
                val error: ErrorResponse = Gson().fromJson(jsonString, ErrorResponse::class.java)
                error.message
            }
            emit(ResultState.Error(errorMessage))
            Log.d(TAG, "get all notification error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "get all notification error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "get all notification error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }
}
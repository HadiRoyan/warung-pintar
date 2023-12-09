package com.capstone.warungpintar.data.repository

import android.util.Log
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.api.ApiDashboardService
import com.capstone.warungpintar.data.remote.model.response.DashboardResponse
import com.capstone.warungpintar.data.remote.model.response.ErrorResponse
import com.capstone.warungpintar.data.remote.model.response.ResponseAPI
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.SocketTimeoutException

class DashboardRepository(private val apiDashboardService: ApiDashboardService) {

    companion object {
        private const val TAG = "DashboardRepository"

        @Volatile
        private var instance: DashboardRepository? = null
        fun getInstance(apiDashboardService: ApiDashboardService) =
            instance ?: synchronized(this) {
                instance ?: DashboardRepository(apiDashboardService)
            }.also { instance = it }
    }

    fun getDashboardUser(email: String): Flow<ResultState<DashboardResponse>> = flow {
        emit(ResultState.Loading)

        try {
            val response: ResponseAPI<DashboardResponse> = apiDashboardService.getDashboard(email)
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
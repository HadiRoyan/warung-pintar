package com.capstone.warungpintar.data.repository

import android.util.Log
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.model.User
import com.capstone.warungpintar.data.remote.api.ApiUserService
import com.capstone.warungpintar.data.remote.model.request.RegisterRequest
import com.capstone.warungpintar.data.remote.model.response.ErrorResponse
import com.capstone.warungpintar.data.remote.model.response.LoginResponse
import com.capstone.warungpintar.data.remote.model.response.ResponseAPI
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.gson.Gson
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException

class UserRepository(
    private val apiUserService: ApiUserService,
    private val auth: FirebaseAuth
) {
    companion object {
        private const val TAG = "UserRepository"

        @Volatile
        private var instance: UserRepository? = null
        private val auth = Firebase.auth
        fun getInstance(apiUserService: ApiUserService) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiUserService, auth)
            }.also { instance = it }
    }

    fun register(
        registerRequest: RegisterRequest
    ): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)

        try {
            val response: ResponseAPI<String> = apiUserService.postRegister(registerRequest)
            emit(ResultState.Success(response.data))
        } catch (e: HttpException) {
            val errorMessage: String = if (e.code() >= 500) {
                "Terjadi kesalahan pada server, coba lagi nanti"
            } else {
                val jsonString = e.response()?.errorBody()?.string()
                val error: ErrorResponse = Gson().fromJson(jsonString, ErrorResponse::class.java)
                error.message
            }
            emit(ResultState.Error(errorMessage))
            Log.d(TAG, "register error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "register error: ${e.message}")
            emit(ResultState.Error("Gagal terhubung dengan server"))
        } catch (e: Exception) {
            Log.d(TAG, "register error: ${e.message}")
            emit(ResultState.Error("Terjadi kesalahan, silakan coba lagi nanti"))
        }
    }

    fun registerToFirebase(email: String, password: String) = channelFlow {
        send(ResultState.Loading)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                launch {
                    if (task.isSuccessful) {
                        val user = task.result.user
                        send(ResultState.Success(user?.email ?: ""))
                        Log.d(TAG, "success create user $email with id ${user?.uid}")

                    } else {
                        val defaultMessage = "Gagal mendaftarkan akun"
                        send(ResultState.Error(task.exception?.message ?: defaultMessage))
                        Log.d(
                            TAG,
                            "failed to create user $email with error ${task.exception?.message}"
                        )
                    }
                }
            }

        awaitClose()
    }

    fun login(email: String, password: String): Flow<ResultState<LoginResponse>> = flow {
        emit(ResultState.Loading)

        try {
            val response: ResponseAPI<LoginResponse> = apiUserService.postLogin(email, password)
            emit(ResultState.Success(response.data))
        } catch (e: HttpException) {
            val errorMessage: String = if (e.code() >= 500) {
                "Terjadi kesalahan pada server, coba lagi nanti"
            } else {
                val jsonString = e.response()?.errorBody()?.string()
                val error: ErrorResponse = Gson().fromJson(jsonString, ErrorResponse::class.java)
                error.message
            }
            emit(ResultState.Error(errorMessage))
            Log.d(TAG, "login error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "login error: ${e.message}")
            emit(ResultState.Error("Gagal terhubung dengan server"))
        } catch (e: Exception) {
            Log.d(TAG, "login error: ${e.message}")
            emit(ResultState.Error("Terjadi kesalahan, silakan coba lagi nanti"))
        }
    }

    fun loginWithFirebase(email: String, password: String) = channelFlow {
        send(ResultState.Loading)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                launch {
                    if (task.isSuccessful) {
                        send(ResultState.Success("Berhasil Masuk"))
                        val user = task.result.user
                        Log.d(TAG, "success login user $email with id ${user?.uid}")

                    } else {
                        val exceptionMessage = task.exception?.message
                        val message = if (exceptionMessage.isNullOrEmpty()) {
                            "Gagal masuk, silahkan coba lagi"
                        } else if (exceptionMessage.contains("credential is incorrect", true)) {
                            "email atau password salah"
                        } else {
                            "Gagal masuk, silahkan coba lagi"
                        }
                        send(ResultState.Error(message))
                        Log.d(
                            TAG,
                            "failed to login user $email with error ${task.exception?.message}"
                        )
                    }
                }
            }

        awaitClose()
    }

    fun getUserDetail(email: String): Flow<ResultState<User>> = flow {
        emit(ResultState.Loading)

        try {
            val response: ResponseAPI<User> = apiUserService.getUserDetail(email)
            emit(ResultState.Success(response.data))
        } catch (e: HttpException) {
            val errorMessage: String = if (e.code() >= 500) {
                "Terjadi kesalahan pada server, coba lagi nanti"
            } else {
                val jsonString = e.response()?.errorBody()?.string()
                val error: ErrorResponse = Gson().fromJson(jsonString, ErrorResponse::class.java)
                error.message
            }
            emit(ResultState.Error(errorMessage))
            Log.d(TAG, "get detail user error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "get detail user error: ${e.message}")
            emit(ResultState.Error("Gagal terhubung dengan server"))
        } catch (e: Exception) {
            Log.d(TAG, "get detail user error: ${e.message}")
            emit(ResultState.Error("Terjadi kesalahan, silakan coba lagi nanti"))
        }
    }
}
package com.capstone.warungpintar.data.repository

import android.util.Log
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.model.Product
import com.capstone.warungpintar.data.remote.api.ApiProductService
import com.capstone.warungpintar.data.remote.model.request.ProductRequest
import com.capstone.warungpintar.data.remote.model.response.ErrorResponse
import com.capstone.warungpintar.data.remote.model.response.ResponseAPI
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import java.net.SocketTimeoutException

class ProductRepository(
    private val apiProductService: ApiProductService
) {

    companion object {
        private const val TAG = "ProductRepository"

        @Volatile
        private var instance: ProductRepository? = null
        fun getInstance(apiProductService: ApiProductService) =
            instance ?: synchronized(this) {
                instance ?: ProductRepository(apiProductService)
            }.also { instance = it }
    }

    fun addProduct(
        imageFile: File,
        imageDetail: ProductRequest
    ): Flow<ResultState<ResponseAPI<String>>> = flow {
        emit(ResultState.Loading)
        val imageFileBody: RequestBody = imageFile.asRequestBody("image/jpeg".toMediaType())
        val gson = Gson()
        val imageDesc = gson.toJson(imageDetail)
        val imageDescBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("image_description", imageDesc)

        try {
            val response: ResponseAPI<String> =
                apiProductService.postAddProduct(imageFileBody, imageDescBody)
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
            Log.d(TAG, "add product error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "add product error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "add product error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

    fun getDetailProduct(productId: Int): Flow<ResultState<ResponseAPI<Product>>> = flow {
        emit(ResultState.Loading)

        try {
            val response: ResponseAPI<Product> =
                apiProductService.getDetailProduct(productId.toString())
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
            Log.d(TAG, "get detail product error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "get detail product error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "get detail product error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }


    fun getAllProduct(): Flow<ResultState<ResponseAPI<List<Product>>>> = flow {
        emit(ResultState.Loading)
        try {
            val response: ResponseAPI<List<Product>> = apiProductService.getAllProduct()
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
            Log.d(TAG, "get all product error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "get all product error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "get all product error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

    fun getListCategoryProduct(): Flow<ResultState<ResponseAPI<List<String>>>> = flow {
        emit(ResultState.Loading)
        try {
            val response: ResponseAPI<List<String>> = apiProductService.getListCategoryProduct()
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
            Log.d(TAG, "get all product error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "get all product error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "get all product error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }
}
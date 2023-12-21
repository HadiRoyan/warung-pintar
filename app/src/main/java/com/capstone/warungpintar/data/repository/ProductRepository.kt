package com.capstone.warungpintar.data.repository

import android.util.Log
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.model.Product
import com.capstone.warungpintar.data.remote.api.ApiProductService
import com.capstone.warungpintar.data.remote.model.request.DeleteProductRequest
import com.capstone.warungpintar.data.remote.model.request.ProductRequest
import com.capstone.warungpintar.data.remote.model.response.ErrorResponse
import com.capstone.warungpintar.data.remote.model.response.HistoryResponse
import com.capstone.warungpintar.data.remote.model.response.ReportResponse
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

    fun getExpiredFromOCR(imageFile: File): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)
        val imageFileBody: RequestBody = imageFile.asRequestBody("image/jpeg".toMediaType())

        try {
            val response = apiProductService.getExpiredDateFromOCR(imageFileBody)
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
            Log.d(TAG, "add product error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "add product error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "add product error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

    fun addProduct(
        imageFile: File,
        imageDetail: ProductRequest
    ): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)
        val imageFileBody: RequestBody = imageFile.asRequestBody("image/jpeg".toMediaType())
        val gson = Gson()
        val imageDesc = gson.toJson(imageDetail)
        val imageDescBody: MultipartBody.Part =
            MultipartBody.Part.createFormData("image_description", imageDesc)

        try {
            val response: ResponseAPI<String> =
                apiProductService.postAddProduct(imageFileBody, imageDescBody)
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
            Log.d(TAG, "add product error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "add product error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "add product error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

    fun getDetailProduct(productId: Int): Flow<ResultState<Product>> = flow {
        emit(ResultState.Loading)

        try {
            val response: ResponseAPI<Product> =
                apiProductService.getDetailProduct(productId.toString())
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
            Log.d(TAG, "get detail product error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "get detail product error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "get detail product error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }


    fun getAllProduct(): Flow<ResultState<List<Product>>> = flow {
        emit(ResultState.Loading)
        try {
            val response: ResponseAPI<List<Product>> = apiProductService.getAllProduct()
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
            Log.d(TAG, "get all product error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "get all product error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "get all product error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

    fun getListCategoryProduct(): Flow<ResultState<List<String>>> = flow {
        emit(ResultState.Loading)
        try {
            val response: ResponseAPI<List<String>> = apiProductService.getListCategoryProduct()
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
            Log.d(TAG, "get all product error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "get all product error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "get all product error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

    fun getListHistories(email: String): Flow<ResultState<List<HistoryResponse>>> = flow {
        emit(ResultState.Loading)

        try {
            val response = apiProductService.getListHistories(email)
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
            Log.d(TAG, "get all history error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "get all history error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "get all history error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

    fun deleteProduct(
        email: String,
        productName: String,
        data: DeleteProductRequest
    ): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)

        try {
            val response: ResponseAPI<String> =
                apiProductService.deleteProduct(email, productName, data)
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
            Log.d(TAG, "Delete Product error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "Delete Product error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "Delete Product error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

    fun getListReport(email: String): Flow<ResultState<List<ReportResponse>>> = flow {
        emit(ResultState.Loading)

        try {
            val response: ResponseAPI<List<ReportResponse>> =
                apiProductService.getListReports(email)
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
            Log.d(TAG, "Get report error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "Get report error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "Get report error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }

    fun getListProductOut(email: String): Flow<ResultState<List<String>>> = flow {
        try {
            val response: ResponseAPI<List<String>> =
                apiProductService.getListProductOut(email)
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
            Log.d(TAG, "Get list product out error: ${e.message}, with response $errorMessage")
        } catch (e: SocketTimeoutException) {
            Log.d(TAG, "Get list product out error: ${e.message}")
            emit(ResultState.Error("Request Timeout"))
        } catch (e: Exception) {
            Log.d(TAG, "Get list product out error: ${e.message}")
            emit(ResultState.Error("Something Wrong, please try again later"))
        }
    }
}
package com.capstone.warungpintar.data.remote.api

import com.capstone.warungpintar.data.model.Product
import com.capstone.warungpintar.data.remote.model.request.DeleteProductRequest
import com.capstone.warungpintar.data.remote.model.response.HistoryResponse
import com.capstone.warungpintar.data.remote.model.response.ReportResponse
import com.capstone.warungpintar.data.remote.model.response.ResponseAPI
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiProductService {

    @POST("api/products")
    @Multipart
    suspend fun postAddProduct(
        @Part("image") file: RequestBody,
        @Part image_description: MultipartBody.Part
    ): ResponseAPI<String>

    @GET("api/products/{productId}")
    suspend fun getDetailProduct(
        @Path("productId") productId: String
    ): ResponseAPI<Product>

    @GET("api/products")
    suspend fun getAllProduct(): ResponseAPI<List<Product>>

    @GET("api/products/categories")
    suspend fun getListCategoryProduct(): ResponseAPI<List<String>>

    @GET("api/product/histories/{email}")
    suspend fun getListHistories(@Path("email") email: String): ResponseAPI<List<HistoryResponse>>

    @DELETE("api/products/out/{email}/{product_name}")
    suspend fun deleteProduct(
        @Path("email") email: String,
        @Path("product_name") productName: String,
        @Body bodyDelete: DeleteProductRequest
    ): ResponseAPI<String>

    @GET("api/report/{email}")
    suspend fun getListReports(@Path("email") email: String): ResponseAPI<List<ReportResponse>>

    @GET("api/products/out/{email}")
    suspend fun getListProductOut(@Path("email") email: String): ResponseAPI<List<String>>


    @POST("api/perform-ocr")
    @Multipart
    suspend fun getExpiredDateFromOCR(@Part("imageDate") imageData: RequestBody): ResponseAPI<String>
}
package com.capstone.warungpintar.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class DeleteProductRequest(

    @field:SerializedName("product_name")
    val productName: String,

    @field:SerializedName("exit_date")
    val exitDate: String,

    @field:SerializedName("product_quantity")
    val productQuantity: Int,

    @field:SerializedName("selling_price")
    val sellingPrice: Int
)

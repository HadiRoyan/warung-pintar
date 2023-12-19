package com.capstone.warungpintar.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class ProductRequest(

    @field:SerializedName("product_name")
    val productName: String,

    @field:SerializedName("entry_date")
    val entryDate: String,

    @field:SerializedName("product_category")
    val productCategory: String,

    @field:SerializedName("product_quantity")
    val productQuantity: Int,

    @field:SerializedName("low_stock")
    val lowStock: Int,

    @field:SerializedName("code_stock")
    val expiredDate: String,

    @field:SerializedName("purchase_price")
    val purchasePrice: Int,

    @field:SerializedName("selling_price")
    val sellingPrice: Int,
)
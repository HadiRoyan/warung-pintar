package com.capstone.warungpintar.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(

    @field:SerializedName("productId")
    val productId: Int,

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
    val codeStock: String,

    @field:SerializedName("purchase_price")
    val purchasePrice: Int,

    @field:SerializedName("selling_price")
    val sellingPrice: Int,

    @field:SerializedName("imageUrl")
    val imageUrl: String,

    @field:SerializedName("expired_date")
    val expiredDate: String
    
) : Parcelable

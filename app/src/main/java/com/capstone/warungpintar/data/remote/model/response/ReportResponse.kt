package com.capstone.warungpintar.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class ReportResponse(

    @field:SerializedName("product_name")
    val productName: String,

    @field:SerializedName("purchase_price")
    val purchasePrice: Int,

    @field:SerializedName("selling_price")
    val sellingPrice: Int,

    @field:SerializedName("exit_date")
    val exitDate: String
)

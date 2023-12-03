package com.capstone.warungpintar.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class HistoryProduct(

    @field:SerializedName("history_id")
    val id: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("product_name")
    val productName: String,

    @field:SerializedName("amount")
    val amount: String,

    @field:SerializedName("date")
    val date: String,

    @field:SerializedName("price")
    val price: String
)
package com.capstone.warungpintar.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class NotificationMessage(

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("store_name")
    val storeName: String,

    @field:SerializedName("product")
    val product: String
)
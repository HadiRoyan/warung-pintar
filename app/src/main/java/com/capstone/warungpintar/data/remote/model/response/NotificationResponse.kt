package com.capstone.warungpintar.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class NotificationResponse(

    @field:SerializedName("store_name")
    val storeName: String,

    @field:SerializedName("notification_id")
    val notificationId: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("product_name")
    val productName: String
)

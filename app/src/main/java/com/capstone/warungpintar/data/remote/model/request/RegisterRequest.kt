package com.capstone.warungpintar.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,

    @field:SerializedName("store_name")
    val storeName: String,

    @field:SerializedName("phone_number")
    val phoneNumber: String,
)
package com.capstone.warungpintar.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class ResponseAPI<T>(

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val data: T
)
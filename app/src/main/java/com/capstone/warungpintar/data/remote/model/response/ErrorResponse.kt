package com.capstone.warungpintar.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("message")
    val message: String
)
package com.capstone.warungpintar.data.remote.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("type")
    val type: String

) : Parcelable
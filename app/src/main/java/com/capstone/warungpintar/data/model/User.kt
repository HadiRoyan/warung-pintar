package com.capstone.warungpintar.data.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class User(

    @field:SerializedName("store_name")
    val storeName: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("email")
    val email: String? = null

) : Parcelable

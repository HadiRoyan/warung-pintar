package com.capstone.warungpintar.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("amount")
	val amount: Int,

	@field:SerializedName("price")
	val price: String,

	@field:SerializedName("history_id")
	val historyId: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("product_name")
	val productName: String
)

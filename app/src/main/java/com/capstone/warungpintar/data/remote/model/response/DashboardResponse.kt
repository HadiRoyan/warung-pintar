package com.capstone.warungpintar.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class DashboardResponse(

    @field:SerializedName("store_data")
    val storeData: StoreData,

    @field:SerializedName("stock_data")
    val stockData: StockData
)

data class StockData(

    @field:SerializedName("product")
    val product: Int,

    @field:SerializedName("entry_product")
    val entryProduct: Int,

    @field:SerializedName("low_stock")
    val lowStock: Int,

    @field:SerializedName("exit_product")
    val exitProduct: Int
)

data class StoreData(

    @field:SerializedName("store_name")
    val storeName: String,

    @field:SerializedName("email")
    val email: String
)

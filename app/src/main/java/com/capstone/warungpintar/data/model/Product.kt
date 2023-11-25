package com.capstone.warungpintar.data.model

data class Product(
    val id: String,
    val name: String,
    val imageUrl: String,
    val entryDate: String,
    val category: String,
    val total: Long,
    val lowStock: Long,
    val kodeStock: String,
    val salePrice: String,
    val buyPrice: String,
    val supplier: String,
)

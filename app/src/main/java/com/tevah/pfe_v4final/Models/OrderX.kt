package com.tevah.pfe_v4final.Models

data class OrderX(
    val createdAt: String,
    val id: Int,
    val orderGroupId: Int,
    val payee: Boolean,
    val product: ProductXX,
    val productId: Int,
    val quantity: Int,
    val updatedAt: String,
    val userId: Int
)
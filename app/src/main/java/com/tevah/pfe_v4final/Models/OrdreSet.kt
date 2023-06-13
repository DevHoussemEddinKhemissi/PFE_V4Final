package com.tevah.pfe_v4final.Models

data class OrdreSet(
    val total: String,
    val products: List<ProductIDQuantity>
)

data class ProductIDQuantity(
    val id: Int,
    val quantity: Int
)
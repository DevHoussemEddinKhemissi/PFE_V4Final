package com.tevah.pfe_v4final.Models

data class ShopXXX(
    val adress: String,
    val createdAt: String,
    val description: String,
    val id: Int,
    val image: String,
    val langitude: String,
    val latitude: String,
    val name: String,
    val products: List<Produit>,
    val updatedAt: String
)
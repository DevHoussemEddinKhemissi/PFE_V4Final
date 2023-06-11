package com.tevah.pfe_v4final.Models

data class Product(
    val createdAt: String,
    val description: String,
    val expirationdate: String,
    val id: Int,
    val image: String,
    val name: String,
    val prix: String,
    val productiondate: String,
    val shop: ShopX,
    val shopId: Int,
    val stock: Int,
    val subcategory: SubcategoryX,
    val subcategoryId: Int,
    val updatedAt: String,
    val valid: Boolean
)
package com.tevah.pfe_v4final.Models

data class ProductX(
    val createdAt: String,
    val description: String,
    val expirationdate: String,
    val id: Int,
    val image: String,
    val name: String,
    val prix: Any,
    val productiondate: String,
    val shop: ShopXX,
    val shopId: Int,
    val stock: Int,
    val subcategory: SubcategoryXX,
    val subcategoryId: Int,
    val updatedAt: String,
    val valid: Boolean
)
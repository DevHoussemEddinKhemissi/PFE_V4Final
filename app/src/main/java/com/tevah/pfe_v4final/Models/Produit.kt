package com.tevah.pfe_v4final.Models

data class Produit(
    val createdAt: String,
    val description: String,
    val expirationdate: String,
    val id: Int,
    val image: String?,
    val name: String,
    val productiondate: String,
    val shop: Shop,
    val shopId: Int,
    val stock: Int,
    val subcategory: Subcategory,
    val subcategoryId: Int,
    val updatedAt: String,
    val valid: Boolean,
    val prix: String
){
    fun getProductImageUrl(): String{
        image?.let {
            return "$it"
        }?: run {
            return "http://192.168.1.14:4242/images/1686417255978.png" //placeholder

        }
    }
}

package com.tevah.pfe_v4final.Models

data class WishlistItem(
    val id: Long,
    val name: String,
    val image: String,
    val stock: Int,
    val valid: Int,
    val price: String
)
package com.tevah.pfe_v4final.Models

data class Card(
    val id: Long,
    val image: String, val name: String, val stock: Int, val price: Double, var quantity: Int)
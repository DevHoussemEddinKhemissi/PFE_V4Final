package com.tevah.pfe_v4final.Models

data class FetchAllProductResponse(
    val description: String,
    val product: List<Produit>
)
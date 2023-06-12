package com.tevah.pfe_v4final.Models

data class OrderResponce(
    val currency: String,
    val order_id: Int,
    val order_key: String,
    val paymentIntentClientSecret: String,
    val stripePublishableKey: String,
    val total: Int,
    val totalShipping: Int,
    val totalTax: Int
)
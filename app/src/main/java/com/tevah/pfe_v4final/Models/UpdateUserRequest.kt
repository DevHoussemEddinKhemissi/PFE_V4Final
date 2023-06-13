package com.tevah.pfe_v4final.Models

data class UpdateUserRequest(
    val email: String,
    val image: String,
    val name: String
)
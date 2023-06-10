package com.tevah.pfe_v4final.Models

data class UserX(
    val email: String,
    val name: String,
    val roles: List<Role>,
    val image: String
)
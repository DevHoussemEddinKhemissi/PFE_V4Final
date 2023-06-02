package com.tevah.pfe_v4final.Models

data class UserRegister(
    val email: String,
    val name: String,
    val password: String,
    val roles: List<String>,
    val tel: String,
    val username: String
)
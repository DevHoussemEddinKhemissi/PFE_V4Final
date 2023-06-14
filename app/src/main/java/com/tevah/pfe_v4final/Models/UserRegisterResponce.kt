package com.tevah.pfe_v4final.Models

data class UserRegisterResponce(
    val code: String,
    val message: String,
    val err: String,
    val user: UserXX
)
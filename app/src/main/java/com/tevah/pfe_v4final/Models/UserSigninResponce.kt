package com.tevah.pfe_v4final.Models

data class UserSigninResponce(
    val accessToken: String,
    val auth: Boolean,
    val code: String,
    val message: String
)
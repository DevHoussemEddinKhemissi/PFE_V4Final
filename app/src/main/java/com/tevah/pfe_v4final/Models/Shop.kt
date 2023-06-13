package com.tevah.pfe_v4final.Models

import com.tevah.pfe_v4final.API.PathImages

data class Shop(
    val id: Int,
    val name: String,
    val adress: String,
    val image: String,
    val description: String,
    val langitude: String,
    val latitude: String,
    val createdAt: String,
    val updatedAt: String
) {
    fun getImagePath(): String {
        val myPath = PathImages.STATIC_PATH_BASE
        val imagepath = image.replaceFirst("%server_tevah_api_endpoint%",myPath)
        return imagepath
    }
}
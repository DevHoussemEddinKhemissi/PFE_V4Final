package com.tevah.pfe_v4final.Models

import com.tevah.pfe_v4final.API.PathImages

data class UserX(
    val email: String,
    val name: String,
    val roles: List<Role>,
    val image: String
) {
    fun getImagePath(): String {
        val myPath = PathImages.STATIC_PATH_BASE
        val imagepath = image.replaceFirst("%server_tevah_api_endpoint%",myPath)
        return imagepath
    }
}
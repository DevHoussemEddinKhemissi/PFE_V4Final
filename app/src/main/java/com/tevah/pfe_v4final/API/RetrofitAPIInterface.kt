package com.tevah.pfe_v4final.API

import com.tevah.pfe_v4final.Models.UserRegister
import com.tevah.pfe_v4final.Models.UserRegisterResponce
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAPIInterface {

    @POST("/api/auth/signup")
    fun createUser(@Body user: UserRegister): Call<UserRegisterResponce>

}
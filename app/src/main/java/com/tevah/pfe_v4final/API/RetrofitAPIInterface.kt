package com.tevah.pfe_v4final.API

import com.tevah.pfe_v4final.Models.UserRegister
import com.tevah.pfe_v4final.Models.UserRegisterResponce
import com.tevah.pfe_v4final.Models.UserSignin
import com.tevah.pfe_v4final.Models.UserSigninResponce
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitAPIInterface {

    @POST("/api/auth/signup")
    fun createUser(@Body user: UserRegister): Call<UserRegisterResponce>
    @POST("/api/auth/signin")
    fun createUser(@Body user: UserSignin): Call<UserSigninResponce>
}
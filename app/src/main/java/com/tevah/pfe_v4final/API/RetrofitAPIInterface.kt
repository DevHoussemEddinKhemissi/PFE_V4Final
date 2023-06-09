package com.tevah.pfe_v4final.API

import com.tevah.pfe_v4final.Models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RetrofitAPIInterface {

    @POST("/api/auth/signup")
    fun CreateUser(@Body user: UserRegister): Call<UserRegisterResponce>
    @POST("/api/auth/signin")
    fun LoginViaEmail(@Body user: UserSignin): Call<UserSigninResponce>
    @GET("/api/test/user")
    fun GetUSER(@Header("Authorization") token: String):Call<UserRetrieve>

}
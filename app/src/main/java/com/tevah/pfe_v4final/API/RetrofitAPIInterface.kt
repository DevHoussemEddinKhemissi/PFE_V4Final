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
    fun GetUSER(@Header("x-access-token") token: String):Call<UserRetrieve>

    @GET("/api/test/findallproducts")
    fun fetchAllProducts(): Call<FetchAllProductResponse>

    @GET("/api/test/FindAllshop")
    fun fetchAllShops(): Call<FetchAllShopsResponse>


    @POST("/api/auth/viagoogle")
    fun LoginViaGoogle(@Body user: LoginViaGoogleRequest): Call<UserSigninResponce>

    @POST("/api/auth/viafacebook")
    fun LoginViaFacebook(@Body user: LogViaFacebookNodeRequest): Call<UserSigninResponce>

}
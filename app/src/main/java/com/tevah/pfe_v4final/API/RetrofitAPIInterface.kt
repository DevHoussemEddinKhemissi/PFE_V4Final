package com.tevah.pfe_v4final.API


import com.tevah.pfe_v4final.Models.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*

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


    @POST("/api/test/FindOneproduct")
    fun getData(@Body productDetailGet: ProductDetailGet): Call<ProduitDetailsPageResponce>

    @POST("/api/sales/createorder")
    fun createPayment(@Header("x-access-token") token: String, @Body order: OrdreSet): Call<OrderResponce>

    @POST("/api/test/FindOneproductWithFK")
    fun tallDataGrip(@Body finAllDataOnProduct: FinAllDataOnProduct): Call<AllDataOnProduct>

    @PUT("/api/user")
    fun updateUser(@Header("x-access-token") token: String, @Body userBody: UpdateUserRequest): Call<UserUpdateResponse>


}
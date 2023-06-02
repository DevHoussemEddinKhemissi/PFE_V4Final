package com.tevah.pfe_v4final.API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilderRetrofit {
    private val client = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.21:4242")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)}
}
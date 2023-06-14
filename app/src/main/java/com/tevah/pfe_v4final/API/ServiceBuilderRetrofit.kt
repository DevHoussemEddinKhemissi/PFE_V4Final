package com.tevah.pfe_v4final.API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilderRetrofit {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Set connection timeout
        .readTimeout(30, TimeUnit.SECONDS) // Set read timeout
        .writeTimeout(30, TimeUnit.SECONDS) // Set write timeout
         .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.19:4242")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)}
}
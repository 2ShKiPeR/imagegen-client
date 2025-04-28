package com.example.imagegenclient.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ImageRetrofitClient {
    private const val BASE_URL = "http://192.168.1.35:8080/"

    val apiService: ImageApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImageApiService::class.java)
    }
}

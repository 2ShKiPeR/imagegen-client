package com.example.imagegenclient.network

import com.example.imagegenclient.model.ImageResponse
import retrofit2.http.Header
import retrofit2.http.POST

interface ImageApiService {
    @POST("/generate")
    suspend fun generateImage(@Header("Authorization") token: String): ImageResponse
}

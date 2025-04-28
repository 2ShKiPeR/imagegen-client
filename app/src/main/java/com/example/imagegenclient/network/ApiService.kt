package com.example.imagegenclient.network

import com.example.imagegenclient.model.AuthRequest
import com.example.imagegenclient.model.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun login(@Body authRequest: AuthRequest): Call<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    fun register(@Body authRequest: AuthRequest): Call<AuthResponse>
}

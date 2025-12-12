package com.example.myapplication.network

import com.example.myapplication.network.dto.ActiveTaskResponseDto
import retrofit2.http.GET
import retrofit2.http.Header

interface TaskApi {
    @GET("api/device/pair/active-task")
    suspend fun getActiveTask(
        @Header("Authorization") token: String
    ): ActiveTaskResponseDto
}
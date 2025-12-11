package com.example.myapplication.network

import com.example.myapplication.network.dto.ActivateRequestDto
import com.example.myapplication.network.dto.ActivateResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface DeviceApi {

    @POST("api/device/pair/activate")
    suspend fun activate(
        @Body request: ActivateRequestDto
    ): ActivateResponseDto
}
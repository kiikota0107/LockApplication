package com.example.myapplication.network.dto

data class ActivateRequestDto(
    val code: String,
    val deviceName: String
)
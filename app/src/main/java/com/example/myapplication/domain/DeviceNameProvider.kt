package com.example.myapplication.domain

import android.os.Build

object DeviceNameProvider {

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
            .trim()
            .replaceFirstChar { it.uppercase() }

        val model = Build.MODEL.trim()

        return if (model.startsWith(manufacturer, ignoreCase = true)) {
            model
        } else {
            "$manufacturer $model"
        }
    }
}
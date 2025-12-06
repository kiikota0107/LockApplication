package com.example.myapplication

import android.content.Context
import android.provider.Settings
import android.text.TextUtils

fun isAccessibilityServiceEnabled(context: Context, serviceName: String): Boolean {
    val accessibilityEnabled = try {
        Settings.Secure.getInt(
            context.contentResolver,
            Settings.Secure.ACCESSIBILITY_ENABLED
        )
    } catch (e: Settings.SettingNotFoundException) {
        0
    }

    if (accessibilityEnabled == 1) {
        val settingValue = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )

        if (!TextUtils.isEmpty(settingValue)) {
            return settingValue!!.contains(serviceName)
        }
    }
    return false
}
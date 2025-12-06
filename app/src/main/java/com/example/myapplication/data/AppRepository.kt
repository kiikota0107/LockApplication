package com.example.myapplication.data

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager

class AppRepository(private val context: Context) {

    @SuppressLint("QueryPermissionsNeeded")
    fun getInstalledApps(): List<AppInfo> {
        val pm = context.packageManager

        val apps = pm.getInstalledApplications(PackageManager.GET_META_DATA)

        return apps
            .filter { app ->
                pm.getLaunchIntentForPackage(app.packageName) != null
            }
            .map { app ->
            val name = pm.getApplicationLabel(app).toString()
            val icon = pm.getApplicationIcon(app.packageName)

            AppInfo(
                appName = name,
                packageName = app.packageName,
                icon = icon
            )
        }.sortedBy { it.appName.lowercase() }
    }
}
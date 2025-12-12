package com.example.myapplication.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.myapplication.LockActivity
import com.example.myapplication.data.LockedAppsStore
import com.example.myapplication.data.datastore.DeviceTokenStore
import com.example.myapplication.network.RetrofitClient

class LockAccessibilityService : AccessibilityService() {

    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return

        val packageName = event.packageName?.toString() ?: return

        if (packageName == this.packageName) return

        Log.d("LockService", "Opened app: $packageName")
        serviceScope.launch {
            val locked = LockedAppsStore.getLockedApps(this@LockAccessibilityService)
            if (packageName !in locked) return@launch

            val tokenStore = DeviceTokenStore.from(this@LockAccessibilityService)
            val token = tokenStore.getToken()
            if (token == null) {
                Log.d("LockService", "Token missing → lock")
                showLock()
                return@launch
            }

            Log.d("LockService", "Token: $token")

            val skipLock = try {
                val result = RetrofitClient.taskApi.getActiveTask("Bearer $token")
                Log.d("LockService", "result: $result")
                result.skipAppLock
            } catch (e: Exception) {
                Log.e("LockService", "API error: ${e.message}")
                false
            }

            if (skipLock) {
                Log.d("LockService", "Server says SkipAppLock=true → NOT locking")
                return@launch
            }

            Log.d("LockService", "SkipAppLock=false → LOCK")
            showLock()
        }
    }

    private fun showLock() {
        val intent = Intent(this, LockActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
    }

    override fun onInterrupt() {}
}
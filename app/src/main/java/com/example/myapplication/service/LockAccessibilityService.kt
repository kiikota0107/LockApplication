package com.example.myapplication.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.runBlocking
import com.example.myapplication.LockActivity
import com.example.myapplication.data.LockedAppsStore

class LockAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return

        val packageName = event.packageName?.toString() ?: return

        if (packageName == this.packageName) return

        Log.d("LockService", "Opened app: $packageName")

        val locked = runBlocking { LockedAppsStore.getLockedApps(this@LockAccessibilityService) }

        if (packageName in locked) {
            Log.d("LockService", "LOCKED! Redirect to LockActivity")

            val intent = Intent(this, LockActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }
    }

    override fun onInterrupt() {}
}
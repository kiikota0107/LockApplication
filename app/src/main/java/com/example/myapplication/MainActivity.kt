package com.example.myapplication

import androidx.compose.runtime.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import android.content.Intent
import android.provider.Settings
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.AppNavHost

class MainActivity : ComponentActivity() {
    private var shouldShowDialog = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                if (shouldShowDialog.value) {
                    PermissionDialog(
                        onConfirm = { openAccessibilitySetting() }
                    )
                }
                AppNavHost()
            }
        }
    }

    override  fun onResume() {
        super.onResume()

        val serviceName = "${packageName}.service.LockAccessibilityService"
        val isEnabled = isAccessibilityServiceEnabled(this, serviceName)

        shouldShowDialog.value = !isEnabled
    }

    private fun openAccessibilitySetting() {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
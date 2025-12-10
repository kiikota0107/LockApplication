package com.example.myapplication

import android.os.Bundle
import android.content.Intent
import android.provider.Settings
import android.content.Context
import androidx.compose.runtime.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.AppNavHost
import com.example.myapplication.data.datastore.DeviceTokenStore
import com.example.myapplication.data.DeviceRepository
import com.example.myapplication.viewmodel.PairingViewModel
import com.example.myapplication.network.RetrofitClient

private val Context.dataStore by preferencesDataStore(name = "device_token_store")

class MainActivity : ComponentActivity() {
    private var shouldShowDialog = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tokenStore = DeviceTokenStore(dataStore)

        val pairingViewModel = PairingViewModel(
            deviceRepository = DeviceRepository(
                api = RetrofitClient.deviceApi,
                tokenStore = tokenStore
            )
        )

        setContent {
            MyApplicationTheme {
                if (shouldShowDialog.value) {
                    PermissionDialog(
                        onConfirm = { openAccessibilitySetting() }
                    )
                }
                AppNavHost(
                    pairingViewModel = pairingViewModel,
                    tokenStore = tokenStore
                )
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
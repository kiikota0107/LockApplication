package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.datastore.DeviceTokenStore
import com.example.myapplication.viewmodel.PairingViewModel

@Composable
fun AppNavHost(
    pairingViewModel: PairingViewModel,
    tokenStore: DeviceTokenStore
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        val token = tokenStore.getToken()
        if (token == null) {
            navController.navigate("pairing") {
                popUpTo(0)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = "app_list"
    ) {
        composable("app_list") {
            AppListScreen()
        }

        composable("pairing") {
            PairingScreen(
                viewModel = pairingViewModel,
                onSuccess = {
                    navController.navigate("app_list") {
                        popUpTo("pairing") { inclusive = true }
                    }
                }
            )
        }
    }
}
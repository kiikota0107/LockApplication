package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.viewmodel.PairingViewModel

@Composable
fun PairingScreen(
    viewModel: PairingViewModel,
    onSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var codeInput by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is PairingViewModel.UiState.Success) {
            onSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "ペアリングコードを入力してください")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = codeInput,
            onValueChange = { codeInput = it },
            label = { Text("例: ABCD-1234") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.activatePairing(codeInput) },
            enabled = uiState !is PairingViewModel.UiState.Loading
        ) {
            Text("ペアリング開始")
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (uiState) {
            is PairingViewModel.UiState.Loading ->
                CircularProgressIndicator()

            is PairingViewModel.UiState.Error ->
                Text(
                    text = (uiState as PairingViewModel.UiState.Error).message ?: "エラーが発生しました",
                    color = MaterialTheme.colorScheme.error
                )

            else -> {}
        }
    }
}
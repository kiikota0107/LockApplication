package com.example.myapplication

import androidx.compose.runtime.*
import androidx.compose.material3.*

@Composable
fun PermissionDialog(onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("権限が必要です") },
        text = {
            Text("このアプリを使用するには「アクセシビリティ権限」が必要です。\n設定画面で権限をONにしてください。")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text("設定へ進む") }
        }
    )
}
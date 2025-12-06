package com.example.myapplication.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.myapplication.data.AppInfo

@Composable
fun AppListItem(
    app: AppInfo,
    isLocked: Boolean,
    onToggle: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            bitmap = app.icon.toImageBitmap(),
            contentDescription = app.appName,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = app.appName,
            modifier = Modifier.weight(1f)
        )

        Switch(
            checked = isLocked,
            onCheckedChange = { onToggle(app.packageName) },
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}
package com.example.myapplication.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.AppListViewModel
import com.example.myapplication.viewmodel.AppListViewModelFactory
import com.example.myapplication.data.AppRepository
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen() {

    // ViewModelの取得
    val context = LocalContext.current
    val viewModel: AppListViewModel = viewModel(
        factory = AppListViewModelFactory(AppRepository(context))
    )

    // アプリ一覧を監視（StateFlow → State へ変換）
    val apps by viewModel.apps.collectAsState()

    // ロック状態を監視（StateFlow → State へ変換）
    val lockedApps by viewModel.lockedApps.collectAsState()

    // 初回だけアプリ一覧を読み込む
    LaunchedEffect(Unit) {
        viewModel.loadApps()
    }

    // 検索バーの入力値
    var searchQuery by remember { mutableStateOf("")}

    // 画面を縦方向に並べる
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // --- ①TopAppBar（上部バー）---
        TopAppBar(
            title = { Text("アプリ一覧") }
        )

        // --- ②検索バー ---
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("検索") }
        )

        // --- ③リスト枠 ---
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(apps) { app ->
                AppListItem(
                    app = app,
                    isLocked = lockedApps.contains(app.packageName),
                    onToggle = { pkg ->
                        viewModel.toggleLock(context, pkg)
                    })
            }
        }
    }
}
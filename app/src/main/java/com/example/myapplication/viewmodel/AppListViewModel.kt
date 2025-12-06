package com.example.myapplication.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.content.Context
import com.example.myapplication.data.AppInfo
import com.example.myapplication.data.AppRepository
import com.example.myapplication.data.LockedAppsStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AppListViewModel(
    private val repository: AppRepository
) : ViewModel() {

    private val _apps = MutableStateFlow<List<AppInfo>>(emptyList())
    val apps: StateFlow<List<AppInfo>> = _apps

    fun loadApps() {
        viewModelScope.launch {
            val result = repository.getInstalledApps()
            _apps.value = result
        }
    }

    private val _lockedApps = MutableStateFlow<Set<String>>(emptySet())
    val lockedApps = _lockedApps

    fun loadLockedApps(context: Context) {
        viewModelScope.launch {
            _lockedApps.value = LockedAppsStore.getLockedApps(context)
        }
    }

    fun toggleLock(context: Context, packageName: String) {
        viewModelScope.launch {
            LockedAppsStore.toggle(context, packageName)
            loadLockedApps(context)
        }
    }

}
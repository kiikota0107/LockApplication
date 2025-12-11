package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.DeviceRepository
import com.example.myapplication.domain.DeviceNameProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PairingViewModel(
    private val deviceRepository: DeviceRepository
) : ViewModel() {

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String?) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    fun activatePairing(code: String) {
        val deviceName = DeviceNameProvider.getDeviceName()

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = deviceRepository.activate(code, deviceName)

            _uiState.value = result.fold(
                onSuccess = { UiState.Success },
                onFailure = { UiState.Error(it.message) }
            )
        }
    }
}
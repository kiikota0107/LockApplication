package com.example.myapplication.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.deviceTokenDataStore by preferencesDataStore("device_token_store")

class DeviceTokenStore(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        fun from(context: Context): DeviceTokenStore {
            return DeviceTokenStore(context.deviceTokenDataStore)
        }
        private val KEY_DEVICE_TOKEN = stringPreferencesKey("device_token")
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { prefs ->
            prefs[KEY_DEVICE_TOKEN] = token
        }
    }

    suspend fun getToken(): String? {
        return dataStore.data
            .map { prefs -> prefs[KEY_DEVICE_TOKEN] }
            .first()
    }

    suspend fun clearToken() {
        dataStore.edit { prefs ->
            prefs.remove(KEY_DEVICE_TOKEN)
        }
    }
}

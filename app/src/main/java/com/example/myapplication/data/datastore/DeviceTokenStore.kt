package com.example.myapplication.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DeviceTokenStore(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
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

package com.example.myapplication.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "locked_apps")

object LockedAppsStore {

    private val LOCKED_PACKAGES = stringSetPreferencesKey("locked_packages")

    suspend fun getLockedApps(context: Context): Set<String> {
        val prefs = context.dataStore.data.first()
        val locked = prefs[LOCKED_PACKAGES] ?: emptySet()
        return locked.filterNot { it == context.packageName }.toSet()
    }

    suspend fun toggle(context: Context, packageName: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[LOCKED_PACKAGES] ?: emptySet()
            prefs[LOCKED_PACKAGES] =
                if (packageName in current) current - packageName
                else current + packageName
        }
    }
}
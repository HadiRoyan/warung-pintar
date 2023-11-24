package com.capstone.warungpintar.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")

class UserPreferences(private val dataStore: DataStore<Preferences>) {

    companion object {
        private const val KEY_EMAIL = "email"
        private const val KEY_TOKEN = "token"

        private val KEY_PREFERENCES_EMAIL = stringPreferencesKey(KEY_EMAIL)
        private val KEY_PREFERENCES_TOKEN = stringPreferencesKey(KEY_TOKEN)

        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(context: Context): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val dataStore = context.dataStore
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    fun saveToken(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { preferences ->
                preferences[KEY_PREFERENCES_TOKEN] = token
            }
        }
    }

    fun getToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[KEY_PREFERENCES_TOKEN]
        }
    }

    fun deleteToken() {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { preferences ->
                preferences.remove(KEY_PREFERENCES_TOKEN)
            }
        }
    }
}
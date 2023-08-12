package kg.ticode.shine.application

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.HiltAndroidApp
val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "LocalStore")
@HiltAndroidApp
class ShineApplication : Application()
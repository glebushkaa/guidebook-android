package com.gm.ai.guidebook.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.gm.ai.guidebook.data.datastore.SettingsDataStoreImpl
import com.gm.ai.guidebook.domain.SettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/26/2023
 */

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    private const val SETTINGS_DATA_STORE = "settings_datastore"
    private const val SETTINGS_DATA_STORE_NAME = "settings_datastore_name"

    @Provides
    @Singleton
    @Named(SETTINGS_DATA_STORE)
    fun provideSettingsPreferences(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(SETTINGS_DATA_STORE_NAME) },
        )
    }

    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @Named(SETTINGS_DATA_STORE) dataStore: DataStore<Preferences>,
    ): SettingsDataStore {
        return SettingsDataStoreImpl(dataStore)
    }
}
package com.qw73.weatherapptask.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.qw73.weatherapptask.data.Repo.MainRepo
import com.qw73.weatherapptask.data.Repo.RepoImpl
import com.qw73.weatherapptask.data.api.ApiHelper
import com.qw73.weatherapptask.data.api.ApiHelperImpl
import com.qw73.weatherapptask.data.db.AppDB
import com.qw73.weatherapptask.data.db.ForecastDao
import com.qw73.weatherapptask.data.api.ApiService
import com.qw73.weatherapptask.data.store.Settings
import com.qw73.weatherapptask.data.store.SettingsImpl
import com.qw73.weatherapptask.util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(moshiConverterFactory)
        .build()

    @Singleton
    @Provides
    fun provideConverterFactory(): MoshiConverterFactory =
        MoshiConverterFactory.create()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideForecastDao(appDataBase: AppDB): ForecastDao = appDataBase.forecastDao()

    @Provides
    @Singleton
    fun provideRepositoryHelper(repository: RepoImpl): MainRepo = repository

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDB = Room
        .databaseBuilder(appContext, AppDB::class.java, AppDB.DATABASE_NAME)
        .build()

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        appContext.dataStore

    @Provides
    @Singleton
    fun provideSettingStore(settingsStore: SettingsImpl): Settings =
        settingsStore
}
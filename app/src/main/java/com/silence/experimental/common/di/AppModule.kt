package com.silence.experimental.common.di

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import androidx.room.Room
import com.silence.experimental.ExperimentalApplication
import com.silence.experimental.common.data.ExperimentalDataBase
import com.silence.experimental.common.data.NetworkHandler
import com.silence.experimental.common.data.PreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DataModule::class])
class AppModule(private val app: ExperimentalApplication) {

    companion object {
        private const val DATA_BASE_NAME = "experimental-database"
    }

    @Singleton
    @Provides
    fun provideApplicationContext(): Context = app

    @Singleton
    @Provides
    fun provideDataBase(context: Context): ExperimentalDataBase {
        return Room.databaseBuilder(context, ExperimentalDataBase::class.java, DATA_BASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideNetworkHandler(cm: ConnectivityManager) = NetworkHandler(cm)

    @Singleton
    @Provides
    fun provideResources(context: Context): Resources = context.resources

    @Singleton
    @Provides
    fun providePreferenceHelper(context: Context) = PreferenceHelper(context)
}
package com.silence.experimental.common.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.silence.experimental.common.data.DataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        private const val DATA_BASE_NAME = "experimental-database"
    }

    @Singleton
    @Provides
    fun provideDataBase(context: Context): RoomDatabase {
        return Room.databaseBuilder(context, DataBase::class.java, DATA_BASE_NAME).build()
    }
}
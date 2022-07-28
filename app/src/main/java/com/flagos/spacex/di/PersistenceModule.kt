package com.flagos.spacex.di

import android.app.Application
import androidx.room.Room
import com.flagos.spacex.data.persistence.AppDatabase
import com.flagos.spacex.data.persistence.LaunchesDao
import com.flagos.spacex.data.persistence.RoomConverters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DB_NAME = "spacex.db"

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application, roomConverters: RoomConverters): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .addTypeConverter(roomConverters)
            .build()
    }

    @Provides
    @Singleton
    fun provideLaunchesDao(appDatabase: AppDatabase): LaunchesDao {
        return appDatabase.launchesDao()
    }

    @Provides
    @Singleton
    fun provideTypeResponseConverter(moshi: Moshi): RoomConverters {
        return RoomConverters(moshi)
    }
}

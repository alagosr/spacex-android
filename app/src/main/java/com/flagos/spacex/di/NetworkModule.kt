package com.flagos.spacex.di

import com.flagos.spacex.data.network.LaunchesClient
import com.flagos.spacex.data.network.LaunchesService
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://api.spacexdata.com/v3/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLaunchesService(retrofit: Retrofit): LaunchesService = retrofit.create(LaunchesService::class.java)

    @Provides
    @Singleton
    fun provideLaunchesClient(launchesService: LaunchesService): LaunchesClient {
        return LaunchesClient(launchesService)
    }
}

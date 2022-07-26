package com.flagos.spacex.di

import com.flagos.spacex.data.LaunchesRepository
import com.flagos.spacex.data.network.LaunchesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideEmployeeDirectoryRepository(
        launchesClient: LaunchesClient,
        ioDispatcher: CoroutineDispatcher
    ): LaunchesRepository = LaunchesRepository(launchesClient, ioDispatcher)
}

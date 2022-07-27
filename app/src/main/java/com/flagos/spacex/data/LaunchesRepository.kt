package com.flagos.spacex.data

import androidx.annotation.WorkerThread
import com.flagos.spacex.data.network.LaunchesClient
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val NUMBER_OF_ITEMS = 20

class LaunchesRepository @Inject constructor(
    private val launchesClient: LaunchesClient,
    private val ioDispatcher: CoroutineDispatcher
) {

    @WorkerThread
    fun fetchLaunches(onStart: () -> Unit, onComplete: () -> Unit, onError: (String?) -> Unit) = flow {
        launchesClient.fetchLaunches()
            .suspendOnSuccess {
                emit(data.sortedByDescending { it.dateInUnix }.take(NUMBER_OF_ITEMS))
            }.onFailure {
                onError(message())
            }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete()
    }.flowOn(ioDispatcher).conflate()
}

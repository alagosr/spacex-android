package com.flagos.spacex.data

import androidx.annotation.WorkerThread
import com.flagos.spacex.data.network.LaunchesClient
import com.flagos.spacex.data.persistence.LaunchesDao
import com.flagos.spacex.domain.LaunchItem
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

private const val NUMBER_OF_ITEMS = 20

class LaunchesRepository @Inject constructor(
    private val launchesClient: LaunchesClient,
    private val launchesDao: LaunchesDao,
    private val ioDispatcher: CoroutineDispatcher
) {

    @WorkerThread
    fun fetchLaunches(onStart: () -> Unit, onComplete: () -> Unit, onError: (String?) -> Unit) = flow {
        val launchesFromDao = launchesDao.getLaunches()
        if (launchesFromDao.isEmpty()) {
            launchesClient.fetchLaunches()
                .suspendOnSuccess {
                    val launchesFromNetwork = data.sortByDate().take(NUMBER_OF_ITEMS)
                    launchesDao.insertLaunches(launchesFromNetwork)
                    emit(launchesFromNetwork)
                }.onFailure {
                    onError(message())
                }
        } else {
            emit(launchesFromDao.sortByDate())
        }
    }.onStart {
        onStart()
    }.onCompletion {
        onComplete()
    }.flowOn(ioDispatcher).conflate()

    private fun List<LaunchItem>.sortByDate(): List<LaunchItem> {
        return this.sortedByDescending { it.dateInUnix }
    }
}

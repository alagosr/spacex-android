package com.flagos.spacex.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flagos.spacex.data.LaunchesRepository
import com.flagos.spacex.domain.LaunchItem
import com.flagos.spacex.utils.getLocalTimeFromUnix
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(private val launchesRepository: LaunchesRepository) : ViewModel() {

    private var _onLaunchStateChanged = MutableLiveData<LaunchState>()
    val onLaunchStateChanged get() = _onLaunchStateChanged

    fun fetchLaunches() {
        val employees = launchesRepository.fetchLaunches(
            onStart = { Log.d("cobashoi", "fetchLaunches: ON START") },
            onComplete = { Log.d("cobashoi", "fetchLaunches: ON COMPLETE") },
            onError = { Log.d("cobashoi", "fetchLaunches: ON ERROR") }
        )

        viewModelScope.launch {
            employees.distinctUntilChanged().collect { list ->
                _onLaunchStateChanged.value = LaunchState.OnListRetrieved(list)
                list.forEachIndexed { index, item ->
                    Log.d(
                        "cobasho",
                        "$index ${item.rocket.name} ${getLocalTimeFromUnix(item.dateInUnix)}"
                    )
                }
            }
        }
    }

    sealed class LaunchState {
        data class OnListRetrieved(val list: List<LaunchItem>) : LaunchState()
    }
}

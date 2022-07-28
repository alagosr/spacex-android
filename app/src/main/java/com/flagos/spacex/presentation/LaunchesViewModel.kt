package com.flagos.spacex.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flagos.spacex.data.LaunchesRepository
import com.flagos.spacex.domain.LaunchItem
import com.flagos.spacex.presentation.LaunchesViewModel.LaunchState.*
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
            onStart = { _onLaunchStateChanged.postValue(OnLoading(true)) },
            onComplete = { _onLaunchStateChanged.postValue(OnLoading(false)) },
            onError = { _onLaunchStateChanged.postValue(OnError(it)) }
        )

        viewModelScope.launch {
            employees.distinctUntilChanged().collect { list ->
                _onLaunchStateChanged.value = if (list.isNotEmpty()) OnListRetrieved(list) else OnEmptyList
            }
        }
    }

    sealed class LaunchState {
        data class OnListRetrieved(val list: List<LaunchItem>) : LaunchState()
        data class OnError(val error: String?) : LaunchState()
        data class OnLoading(val loading: Boolean) : LaunchState()
        object OnEmptyList : LaunchState()
    }
}

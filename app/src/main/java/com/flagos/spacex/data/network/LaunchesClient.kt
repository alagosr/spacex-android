package com.flagos.spacex.data.network

import com.flagos.spacex.domain.LaunchItem
import com.skydoves.sandwich.ApiResponse
import javax.inject.Inject

class LaunchesClient @Inject constructor(private val launchesService: LaunchesService) {

    suspend fun fetchLaunches(): ApiResponse<List<LaunchItem>> = launchesService.fetchLaunches()
}

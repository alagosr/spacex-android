package com.flagos.spacex.data.network

import com.flagos.spacex.domain.LaunchItem
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface LaunchesService {

    @GET("launches")
    suspend fun fetchLaunches(): ApiResponse<List<LaunchItem>>
}

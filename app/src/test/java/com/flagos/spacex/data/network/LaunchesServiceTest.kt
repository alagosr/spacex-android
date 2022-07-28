package com.flagos.spacex.data.network

import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchesServiceTest : ApiAbstract<LaunchesService>() {

    private lateinit var service: LaunchesService

    @Before
    fun initService() {
        service = createService(LaunchesService::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun fetchPokemonListFromNetworkTest() = runTest {
        enqueueResponse("/launches.json")
        val response = service.fetchLaunches()
        val responseBody = requireNotNull((response as ApiResponse.Success).data)

        assertThat(responseBody[0].flightNumber, `is`(1))
        assertThat(responseBody[0].missionName, `is`("FalconSat"))
        assertThat(responseBody[0].links.missionPatch, `is`("https://images2.imgbox.com/40/e3/GypSkayF_o.png"))
    }

}
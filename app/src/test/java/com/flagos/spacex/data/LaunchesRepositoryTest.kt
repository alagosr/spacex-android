package com.flagos.spacex.data

import app.cash.turbine.test
import com.flagos.spacex.MainCoroutinesRule
import com.flagos.spacex.MockUtil
import com.flagos.spacex.data.network.LaunchesClient
import com.flagos.spacex.data.network.LaunchesService
import com.flagos.spacex.data.persistence.LaunchesDao
import com.nhaarman.mockitokotlin2.*
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchesRepositoryTest {

    private lateinit var launchesRepository: LaunchesRepository
    private lateinit var launchesClient: LaunchesClient
    private val launchesService: LaunchesService = mock()
    private val launchesDao: LaunchesDao = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        launchesClient = LaunchesClient(launchesService)
        launchesRepository = LaunchesRepository(launchesClient, launchesDao, coroutinesRule.testDispatcher)
    }

    @Test
    fun fetchLaunchesListFromNetworkTest() = runTest {
        val mockData = MockUtil.mockLaunchItemList()
        whenever(launchesDao.getLaunches()).thenReturn(emptyList())
        whenever(launchesService.fetchLaunches()).thenReturn(ApiResponse.of { Response.success(mockData) })

        launchesRepository.fetchLaunches(onStart = {}, onComplete = {}, onError = {}).test {
            val expectItem = awaitItem()[0]
            assertEquals(expectItem.uid, 0)
            assertEquals(expectItem.flightNumber, 1)
            assertEquals(expectItem.missionName, "FalconSat")
            assertEquals(expectItem, mockData[0])
            awaitComplete()
        }

        verify(launchesDao, atLeastOnce()).getLaunches()
        verify(launchesService, atLeastOnce()).fetchLaunches()
        verify(launchesDao, atLeastOnce()).insertLaunches(mockData)
        verifyNoMoreInteractions(launchesService)
    }

    @Test
    fun fetchLaunchesListFromDatabaseTest() = runTest {
        val mockData = MockUtil.mockLaunchItemList()
        whenever(launchesDao.getLaunches()).thenReturn(mockData)

        launchesRepository.fetchLaunches(onStart = {}, onComplete = {}, onError = {}).test {
            val expectItem = awaitItem()[0]
            assertEquals(expectItem.uid, 0)
            assertEquals(expectItem.flightNumber, 1)
            assertEquals(expectItem.missionName, "FalconSat")
            assertEquals(expectItem, mockData[0])
            awaitComplete()
        }

        verify(launchesDao, atLeastOnce()).getLaunches()
    }
}

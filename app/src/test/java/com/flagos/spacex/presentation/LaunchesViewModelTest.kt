package com.flagos.spacex.presentation

import app.cash.turbine.test
import com.flagos.spacex.MainCoroutinesRule
import com.flagos.spacex.MockUtil
import com.flagos.spacex.data.LaunchesRepository
import com.flagos.spacex.data.network.LaunchesClient
import com.flagos.spacex.data.network.LaunchesService
import com.flagos.spacex.data.persistence.LaunchesDao
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LaunchesViewModelTest {

    private lateinit var launchesViewModel: LaunchesViewModel
    private lateinit var launchesRepository: LaunchesRepository
    private val launchesService: LaunchesService = mock()
    private val launchesClient: LaunchesClient = LaunchesClient(launchesService)
    private val launchesDao: LaunchesDao = mock()

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun setup() {
        launchesRepository = LaunchesRepository(launchesClient, launchesDao, coroutinesRule.testDispatcher)
        launchesViewModel = LaunchesViewModel(launchesRepository)
    }

    @Test
    fun fetchPokemonListTest() = runTest {
        val mockData = MockUtil.mockLaunchItemList()
        whenever(launchesDao.getLaunches()).thenReturn(mockData)

        launchesRepository.fetchLaunches(onStart = {}, onComplete = {}, onError = {}).test {
            val item = awaitItem()
            assertEquals(item[0].uid, 0)
            assertEquals(item[0].flightNumber, 1)
            assertEquals(item[0].missionName, "FalconSat")
            assertEquals(item, MockUtil.mockLaunchItemList())
            awaitComplete()
        }

        launchesViewModel.fetchLaunches()

        verify(launchesDao, atLeastOnce()).getLaunches()
    }
}

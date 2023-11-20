package com.mindera.rocketscience.presentation

import app.cash.turbine.test
import com.mindera.rocketscience.BaseUnitTest
import com.mindera.rocketscience.domain.common.UiState
import com.mindera.rocketscience.domain.launcheslist.LaunchesRepository
import com.mindera.rocketscience.presentation.launcheslist.LaunchesViewModel
import com.mindera.rocketscience.presentation.launcheslist.SortOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class LaunchesViewModelTest : BaseUnitTest() {

    @Mock
    private lateinit var repository: LaunchesRepository

    private lateinit var viewModel: LaunchesViewModel

    override fun setUp() {
        super.setUp()
        viewModel = LaunchesViewModel(repository)
    }

    @Test
    fun `test when fetching the launches list, the loading state is shown before success`() = runTest {
        Mockito.`when`(repository.getLaunchesList()).thenReturn(flowOf(launchesResponse))
        viewModel.fetchLaunches()

        viewModel.state.test {
            assertEquals(UiState.Loading, awaitItem())
            assertTrue(awaitItem() is UiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test when fetching the launches list, if an error occur show error state with its message`() = runTest {
        Mockito.`when`(repository.getLaunchesList()).thenReturn(flowOf(errorResponse))
        viewModel.fetchLaunches()

        viewModel.state.test {
            awaitItem() // Loading state

            val errorItem = awaitItem()
            assertTrue(errorItem is UiState.Error)
            assertEquals(errorResponse.message, (errorItem as UiState.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test when fetching the launches years, a set of years is returned in yearsState`() = runTest {
        Mockito.`when`(repository.getLaunchesList()).thenReturn(flowOf(launchesResponse))
        viewModel.fetchLaunches()
        advanceUntilIdle()
        viewModel.fetchYears()
        advanceUntilIdle()

        viewModel.yearsState.test {
            val yearItem = awaitItem()
            assertTrue(yearItem is Set<String>)
            assertEquals(yearsSet, yearItem)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test when applying a filter by year to the launches list should show success state with the applied filter`() = runTest {
        Mockito.`when`(repository.getLaunchesList()).thenReturn(flowOf(launchesResponse))
        viewModel.fetchLaunches()
        advanceUntilIdle()
        viewModel.fetchYears()
        advanceUntilIdle()
        viewModel.applyFilters(listOf("2022"))

        viewModel.state.test {
            awaitItem() // Success state for fetchLaunches call
            val filteredItem = awaitItem()
            assertTrue(filteredItem is UiState.Success)
            assertEquals("2022", (filteredItem as UiState.Success).data[0].launchYear)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test when applying a filter by year and only successful launches to the launches list should show success state with the applied filter`() = runTest {
        Mockito.`when`(repository.getLaunchesList()).thenReturn(flowOf(launchesResponse))
        viewModel.fetchLaunches()
        advanceUntilIdle()
        viewModel.fetchYears()
        advanceUntilIdle()
        viewModel.applyFilters(
            years = listOf("2023"),
            onlySuccessful = true
        )

        viewModel.state.test {
            awaitItem() // Success state for fetchLaunches call
            val filteredItem = awaitItem()
            assertTrue(filteredItem is UiState.Success)
            assertEquals(2, (filteredItem as UiState.Success).data.size)
            assertEquals("2023", filteredItem.data[0].launchYear)
            assertTrue(filteredItem.data[0].wasLaunchSuccessful)
            assertEquals("2023", filteredItem.data[1].launchYear)
            assertTrue(filteredItem.data[1].wasLaunchSuccessful)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test when applying a filter by year, only successful launches and sorted in descending order to the launches list should show success state with the applied filter`() = runTest {
        Mockito.`when`(repository.getLaunchesList()).thenReturn(flowOf(launchesResponse))
        viewModel.fetchLaunches()
        advanceUntilIdle()
        viewModel.fetchYears()
        advanceUntilIdle()
        viewModel.applyFilters(
            years = listOf("2023"),
            onlySuccessful = true,
            SortOrder.DESC
        )

        viewModel.state.test {
            awaitItem() // Success state for fetchLaunches call
            val filteredItem = awaitItem()
            assertTrue(filteredItem is UiState.Success)
            assertEquals(2, (filteredItem as UiState.Success).data.size)
            assertEquals("2023", filteredItem.data[0].launchYear)
            assertTrue(filteredItem.data[0].wasLaunchSuccessful)
            assertEquals("mission4", filteredItem.data[0].missionName)
            assertEquals("2023", filteredItem.data[1].launchYear)
            assertTrue(filteredItem.data[1].wasLaunchSuccessful)
            assertEquals("mission1", filteredItem.data[1].missionName)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

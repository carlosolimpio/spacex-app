package com.mindera.rocketscience.presentation

import app.cash.turbine.test
import com.mindera.rocketscience.BaseUnitTest
import com.mindera.rocketscience.domain.common.UiState
import com.mindera.rocketscience.domain.companyinfo.CompanyRepository
import com.mindera.rocketscience.presentation.companyinfo.CompanyViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class CompanyViewModelTest : BaseUnitTest() {

    @Mock
    private lateinit var repository: CompanyRepository

    private lateinit var viewModel: CompanyViewModel

    override fun setUp() {
        super.setUp()
        viewModel = CompanyViewModel(repository)
    }

    @Test
    fun `test when fetching the company info, the loading state is shown before success`() = runTest {
        Mockito.`when`(repository.getCompany()).thenReturn(flowOf(companyResponse))
        viewModel.fetchCompany()

        viewModel.state.test {
            assertEquals(UiState.Loading, awaitItem())
            assertTrue(awaitItem() is UiState.Success)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test when fetching the company info, if an error occur show error state with its message`() = runTest {
        Mockito.`when`(repository.getCompany()).thenReturn(flowOf(errorResponse))
        viewModel.fetchCompany()

        viewModel.state.test {
            awaitItem() // Loading state

            val errorItem = awaitItem()
            assertTrue(errorItem is UiState.Error)
            assertEquals(errorResponse.message, (errorItem as UiState.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}

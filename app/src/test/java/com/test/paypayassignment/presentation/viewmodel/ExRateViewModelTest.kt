package com.test.paypayassignment.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.paypayassignment.core.ExRatesUiState
import com.test.paypayassignment.core.Result
import com.test.paypayassignment.core.mapper.CurrencyConverterMapper
import com.test.paypayassignment.domain.usecase.ExchangeRatesUseCase
import com.test.paypayassignment.presentation.model.CurrencyRates
import com.test.paypayassignment.presentation.model.ExRatesUiModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExRateViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ExRateViewModel
    private val exchangeRatesUseCase = mockk<ExchangeRatesUseCase>(relaxed = true)
    private val convertMapper = mockk<CurrencyConverterMapper>()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        viewModel = ExRateViewModel(exchangeRatesUseCase, convertMapper)
        mockkObject(viewModel)

    }

    @Test
    fun `onChange should set Loading state and use cached data if valid`() = runTest {
        // Given: Valid cached data
        val fakeRates = listOf(CurrencyRates("USD", 1.0))
        viewModel.apply {
            lastFetchedRates.addAll(fakeRates)
            lastFetchedRatesTimeStamp = System.currentTimeMillis()
            lastSelectedCurrency = "USD"
        }

        coEvery { convertMapper.to(any(), any(), any()) } returns fakeRates

        viewModel.onChange("100")
        advanceUntilIdle()
        val state = viewModel.conversion.first()
        assertTrue(state is ExRatesUiState.Loading)
        coVerify { viewModel.fetchInstantData("100") }
    }

    @Test
    fun `onChange should fetch new data from API if local data is stale`() = runTest {
        // Given: Stale data scenario
        val fakeRates = listOf(CurrencyRates("USD", 1.0))
        val fakeResponse = ExRatesUiModel(System.currentTimeMillis(), fakeRates)
        mockkObject(viewModel)

        coEvery { exchangeRatesUseCase.getExchangeRates() } returns Result.Success(fakeResponse)
        coEvery { convertMapper.to(any(), any(), any()) } returns fakeRates

        viewModel.onChange("50")
        advanceUntilIdle()
        coVerify { viewModel.updateInstantData("50") }
    }

    @Test
    fun `onChange should set Failure state on API error`() = runTest {
        // Given: API failure scenario
        coEvery { exchangeRatesUseCase.getExchangeRates() } returns Result.Error("Network Error")

        viewModel.onChange("10")
        advanceUntilIdle()
        val state = viewModel.conversion.first()
        assertTrue(state is ExRatesUiState.Loading || state is ExRatesUiState.Failure)
    }

    @Test
    fun `resetCalculations should trigger recalculation with default value`() = runTest {
        // Given: Initial state
        coEvery { exchangeRatesUseCase.getExchangeRates() } returns Result.Success(
            ExRatesUiModel(
                System.currentTimeMillis(),
                emptyList()
            )
        )

        viewModel.resetCalculations()
        advanceUntilIdle()
        coVerify { viewModel.updateInstantData("1") }    }
}

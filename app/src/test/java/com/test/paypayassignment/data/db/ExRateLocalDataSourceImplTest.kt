package com.test.paypayassignment.data.db

import com.test.paypayassignment.core.Result
import com.test.paypayassignment.data.db.dao.ExRateDao
import com.test.paypayassignment.data.db.model.ExRatesDbModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExRateLocalDataSourceImplTest {

    @MockK
    private lateinit var appDatabase: AppDatabase

    @MockK
    private lateinit var exchangeRateDao: ExRateDao

    private lateinit var dataSource: ExRateLocalDataSourceImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { appDatabase.exchangeRateDao() } returns exchangeRateDao
        dataSource = ExRateLocalDataSourceImpl(appDatabase)
    }

    @Test
    fun `insertExRates should insert data successfully`() = runTest {
        // Given
        val exRatesDbModel = ExRatesDbModel(12, "2.1")

        // When
        dataSource.insertExRates(exRatesDbModel)

        // Then
        coVerify { exchangeRateDao.insertExchangeRate(exRatesDbModel) }
    }

    @Test
    fun `getLocalExRates should return Success with data`() = runTest {
        // Given
        val expectedExRates = ExRatesDbModel(123, "1.0")
        coEvery { exchangeRateDao.getAllExchangeRates() } returns expectedExRates

        // When
        val result = dataSource.getLocalExRates()

        // Then
        assertTrue(result is Result.Success)
        assertEquals(expectedExRates, (result as Result.Success).data)
        coVerify { exchangeRateDao.getAllExchangeRates() }
    }

    @Test
    fun `getLocalExRates should return Error when data is empty`() = runTest {
        // Given
        val emptyExRates = ExRatesDbModel(0, "")
        coEvery { exchangeRateDao.getAllExchangeRates() } returns emptyExRates

        // When
        val result = dataSource.getLocalExRates()

        // Then
        assertTrue(result is Result.Error)
        assertEquals("Error Occurred While Fetching Data from Local Database", (result as Result.Error).message)
        coVerify { exchangeRateDao.getAllExchangeRates() }
    }

    @Test
    fun `getLocalExRates should return Error on exception`() = runTest {
        // Given
        coEvery { exchangeRateDao.getAllExchangeRates() } throws RuntimeException("Database error")

        // When
        val result = dataSource.getLocalExRates()

        // Then
        assertTrue(result is Result.Error)
        assertEquals("Database error", (result as Result.Error).message)
        coVerify { exchangeRateDao.getAllExchangeRates() }
    }

    @Test
    fun `insertExRates should handle exception gracefully`() = runTest {
        // Given
        val exRatesDbModel = ExRatesDbModel(1234, "1.0")
        coEvery { exchangeRateDao.insertExchangeRate(any()) } throws RuntimeException("Database error")

        // When
        dataSource.insertExRates(exRatesDbModel)

        // Then : verify that the function is called, even in case of error
        coVerify { exchangeRateDao.insertExchangeRate(exRatesDbModel) }

    }

    @After
    fun teardown() {
        appDatabase.close()
    }
}
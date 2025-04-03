package com.test.paypayassignment.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.paypayassignment.data.db.model.ExRatesDbModel

@Dao
interface ExRateDao {

    /**
     * Get all exchange rates
     * @return the exchange rates from the table
     */
    @Query("SELECT * FROM exchange_rates")
    suspend fun getAllExchangeRates(): ExRatesDbModel

    /**
     * Insert an exchange rate in the database. If the currency already exists, replace it.
     * @param exRatesDbModel the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insertExchangeRate(exRatesDbModel: ExRatesDbModel)

    /**
     * Delete all users.
     */
    @Query("DELETE FROM exchange_rates")
    fun deleteAllExchangeRates()


}
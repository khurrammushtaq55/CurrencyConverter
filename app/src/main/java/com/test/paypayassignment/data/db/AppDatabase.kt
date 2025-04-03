package com.test.paypayassignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.paypayassignment.data.db.dao.ExRateDao
import com.test.paypayassignment.data.db.model.ExRatesDbModel

@Database(entities = [ExRatesDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun exchangeRateDao(): ExRateDao

}
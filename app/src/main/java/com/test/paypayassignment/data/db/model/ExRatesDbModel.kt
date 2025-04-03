package com.test.paypayassignment.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exchange_rates")
data class ExRatesDbModel(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "rates") val rates: String?,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long=System.currentTimeMillis()
)
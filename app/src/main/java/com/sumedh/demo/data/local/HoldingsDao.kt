package com.sumedh.demo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sumedh.demo.data.local.entity.HoldingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HoldingsDao {
    @Query("SELECT * FROM holdings")
    fun observeHoldings(): Flow<List<HoldingEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<HoldingEntity>)
}
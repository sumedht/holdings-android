package com.sumedh.demo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sumedh.demo.data.local.entity.HoldingEntity

@Database(
    entities = [HoldingEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun holdingsDao(): HoldingsDao
}
package com.sumedh.demo.di

import android.content.Context
import androidx.room.Room
import com.sumedh.demo.data.local.AppDatabase
import com.sumedh.demo.data.local.dao.HoldingsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "holdings_db"
        ).build()
    }

    @Provides
    fun provideHoldingsDao(
        database: AppDatabase
    ): HoldingsDao = database.holdingsDao()

}
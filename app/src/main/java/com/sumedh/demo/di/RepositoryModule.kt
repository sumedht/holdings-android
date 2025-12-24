package com.sumedh.demo.di

import com.sumedh.demo.data.local.HoldingsDao
import com.sumedh.demo.data.remote.HoldingsApi
import com.sumedh.demo.data.repository.HoldingsRepositoryImpl
import com.sumedh.demo.domain.repository.HoldingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideHoldingsRepository(
        api: HoldingsApi,
        dao: HoldingsDao
    ): HoldingsRepository =
        HoldingsRepositoryImpl(api, dao)
}
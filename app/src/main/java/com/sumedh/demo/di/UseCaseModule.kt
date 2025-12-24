package com.sumedh.demo.di

import com.sumedh.demo.domain.repository.HoldingsRepository
import com.sumedh.demo.domain.usecase.GetHoldingsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetHoldingsUseCase(
        repository: HoldingsRepository
    ): GetHoldingsUseCase =
        GetHoldingsUseCase(repository)
}
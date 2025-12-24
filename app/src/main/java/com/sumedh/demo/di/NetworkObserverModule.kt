package com.sumedh.demo.di

import com.sumedh.demo.data.network.ConnectivityObserver
import com.sumedh.demo.data.network.ConnectivityObserverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkObserverModule {

    @Provides
    @Singleton
    fun provideConnectivityObserver(
        impl: ConnectivityObserverImpl
    ): ConnectivityObserver = impl
}
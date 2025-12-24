package com.sumedh.demo.fake

import com.sumedh.demo.data.network.ConnectivityObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

open class FakeConnectivityObserver : ConnectivityObserver {

    private val state = MutableStateFlow(true)

    override fun observe(): Flow<Boolean> = state
}

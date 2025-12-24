package com.sumedh.demo.common

import com.sumedh.demo.domain.error.AppError

sealed class Resource<T>(val data: T? = null, val error: AppError? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(error: AppError, data: T? = null): Resource<T>(data, error)
    class Loading<T>(): Resource<T>()
}
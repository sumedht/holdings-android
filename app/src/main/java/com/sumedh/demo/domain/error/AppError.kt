package com.sumedh.demo.domain.error

sealed class AppError {

    object NetworkUnavailable : AppError()

    data class ApiError(
        val code: Int,
        val message: String?
    ) : AppError()

    object ParsingError : AppError()

    object EmptyCache : AppError()

    data class Unknown(val throwable: Throwable) : AppError()
}
package com.sumedh.demo.ui

import com.sumedh.demo.domain.error.AppError

fun AppError.toUiMessage(): String =
    when (this) {
        is AppError.NetworkUnavailable ->
            "No internet connection"

        is AppError.ApiError ->
            "Something went wrong. Please try again"

        is AppError.ParsingError ->
            "Unexpected response from server"

        is AppError.EmptyCache ->
            "No holdings available"

        is AppError.Unknown ->
            "Something went wrong"
    }
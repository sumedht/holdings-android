package com.sumedh.demo.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HoldingsResponse(
    @SerializedName("data")
    val data: HoldingsData
)

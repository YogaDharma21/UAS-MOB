package com.example.kantinapplication.modelsdata

import com.google.gson.annotations.SerializedName

data class ResponseDashboard(
    @SerializedName("error")
    val status: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: DashboardItem
)
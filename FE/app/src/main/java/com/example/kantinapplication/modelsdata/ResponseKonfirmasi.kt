package com.example.kantinapplication.modelsdata

import com.google.gson.annotations.SerializedName

data class ResponseKonfirmasi(
    @SerializedName("error") val status: Boolean,
    @SerializedName("message") val message: String
)

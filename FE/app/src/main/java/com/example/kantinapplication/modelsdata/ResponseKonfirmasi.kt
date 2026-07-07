package com.example.kantinapplication.modelsdata

import com.google.gson.annotations.SerializedName

data class ProductInsertResponse(
    @SerializedName("error") val status: Boolean,
    @SerializedName("message") val message: String
)
package com.example.kantinapplication.access

import android.util.Log
import com.example.kantinapplication.modelsdata.ApiService
import okhttp3.OkHttpClient
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://apolitical-daniell-ichthyologically.ngrok-free.dev/"
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            val responseBody = response.body
            val raw = responseBody?.string()

            Log.d("RAW_HTTP", raw ?: "null")
            response.newBuilder()
                .body(raw?.toResponseBody(responseBody.contentType()))
                .build()
        }
        .build()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

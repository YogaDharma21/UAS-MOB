package com.example.kantinapplication.access

import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.kantinapplication.modelsdata.OrdersCallback
import kotlinx.coroutines.launch

class CRUDOrders(
    private val activity: ComponentActivity,
    private val callback: OrdersCallback
) {
    private val apiService: ApiService = RetrofitClient.instance
    fun getAllOrders() {
        activity.lifecycleScope.launch {
            try {
                val response = apiService.getOrders()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { (status, message, data) ->
                        if (!status) {
                            callback.onLoadOrders(data)
                        } else {
                            callback.onError(message)
                        }
                    }
                } else {
                    callback.onError("Response gagal: ${response.code()}")
                }
            } catch (e: Exception) {
                callback.onError(e.message ?: "Terjadi kesalahan")
            }
        }
    }
    fun insertOrder(
        noNota: String,
        metodePembayaran: String,
        keterangan: String,
        idProduk: Int,
        jumlah: Int
    ) {
        activity.lifecycleScope.launch {
            try {
                val response = apiService.insertOrder(
                    noNota,
                    metodePembayaran,
                    keterangan,
                    idProduk,
                    jumlah
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { (status, message) ->
                        if (!status) {
                            callback.onInsertSuccess(message)
                        } else {
                            callback.onError(message)
                        }
                    }
                } else {
                    callback.onError("Response gagal: ${response.code()}")
                }
            } catch (e: Exception) {
                callback.onError(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    fun updateOrder(idTransaksi: Int) {
        activity.lifecycleScope.launch {
            try {
                val response = apiService.updateOrder(idTransaksi)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { (status, message) ->
                        if (!status) {
                            callback.onUpdateSuccess(message)
                        } else {
                            callback.onError(message)
                        }
                    }
                } else {
                    callback.onError("Response gagal: ${response.code()}")
                }
            } catch (e: Exception) {
                callback.onError(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}
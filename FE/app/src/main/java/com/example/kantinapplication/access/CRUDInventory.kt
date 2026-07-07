package com.example.kantinapplication.access

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.kantinapplication.modelsdata.ApiService
import com.example.kantinapplication.modelsdata.InventoryCallback
import com.example.kantinapplication.modelsdata.ProductItem
import kotlinx.coroutines.launch

class CRUDInventory(
    private val activity: ComponentActivity,
    private val callback: InventoryCallback
) {
    private val apiService: ApiService = RetrofitClient.instance

    fun getAllInventory() {
        activity.lifecycleScope.launch {
            try {
                val response = apiService.getProducts()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { (status, message, data) ->
                        if (!status) {
                            data.let {
                                callback.onLoadInventory(it)
                            }
                        } else {
                            Toast.makeText(activity, "Load Data Gagal: $message", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(activity, "Error: ${e.message.toString()}", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun insertInventory(nama: String, harga: String, stok: String) {
        activity.lifecycleScope.launch {
            try {
                val response = apiService.insertProduct(nama, harga, stok)
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { (status, message) ->
                        if (!status) {
                            callback.onInsertSuccess(message)
                        } else {
                            callback.onError(message)
                        }
                    }
                }
            } catch (e: Exception) {
                callback.onError(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    fun updateInventory(idProduk: Int, nama: String, harga: String, stok: String) {
        activity.lifecycleScope.launch {
            try {
                val response = apiService.updateProduct(idProduk, nama, harga, stok)
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

    fun getAllInventoryForSpinner(onSuccess: (List<ProductItem>) -> Unit) {
        activity.lifecycleScope.launch {
            try {
                val response = apiService.getProducts()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && !body.status) {
                        onSuccess(body.data)
                    }
                }
            } catch (e: Exception) {
                callback.onError(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}

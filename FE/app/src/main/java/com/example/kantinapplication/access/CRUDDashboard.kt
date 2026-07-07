package com.example.kantinapplication.access

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.kantinapplication.modelsdata.DashboardCallback
import kotlinx.coroutines.launch

class CRUDDashboard(
    private val activity: ComponentActivity,
    private val callback: DashboardCallback) {
    private val apiService = RetrofitClient.instance

    fun getDashboardData() {
        activity.lifecycleScope.launch {
            try {
                val response = apiService.getDashboard()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { (status, message, data) ->
                        if (!status) {
                            data.let {
                                callback.onLoadDashboard(data)
                            }
                        } else {
                            Toast.makeText(
                                activity,
                                "Load Data Gagal : $message",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(
                    activity,
                    "Error : ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

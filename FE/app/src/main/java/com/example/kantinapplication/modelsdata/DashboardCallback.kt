package com.example.kantinapplication.modelsdata

interface DashboardCallback {
    fun onLoadDashboard(data: DashboardItem)
    fun onError(message: String)
}
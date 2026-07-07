package com.example.kantinapplication.modelsdata

interface OrdersCallback {
    fun onLoadOrders(data: List<OrderItem>)
    fun onInsertSuccess(message: String)
    fun onUpdateSuccess(message: String)
    fun onError(message: String)
}

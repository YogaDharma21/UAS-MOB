package com.example.kantinapplication.modelsdata

interface InventoryCallback {
    fun onLoadInventory(data: List<ProductItem>)
    fun onInsertSuccess(message: String)
    fun onUpdateSuccess(message: String)
    fun onError(message: String)
}

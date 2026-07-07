package com.example.kantinapplication.modelsdata

import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("id_produk") val idProduk: Int,
    @SerializedName("nama_produk") val namaProduk: String,
    @SerializedName("harga") val harga: Int,
    @SerializedName("stok") val stok: Int
)


package com.example.kantinapplication.modelsdata

import com.google.gson.annotations.SerializedName

data class DashboardItem(

    @SerializedName("pendapatan_hari_ini")
    val pendapatanHariIni: Int,

    @SerializedName("produk_terlaris")
    val produkTerlaris: List<ProdukTerlaris>,

    @SerializedName("stok_menipis")
    val stokMenipis: List<StokMenipis>,

    @SerializedName("jumlah_barang_mau_habis")
    val jumlahBarangMauHabis: Int
)

data class ProdukTerlaris(

    @SerializedName("nama_produk")
    val namaProduk: String,

    @SerializedName("jumlah_terjual")
    val jumlahTerjual: Int
)

data class StokMenipis(

    @SerializedName("nama_produk")
    val namaProduk: String,

    @SerializedName("stok")
    val stok: Int
)
package com.example.kantinapplication.modelsdata

import com.google.gson.annotations.SerializedName

data class OrdersItem(
    @SerializedName("id_transaksi") val idTransaksi: Int,
    @SerializedName("no_nota") val noNota: String,
    @SerializedName("tanggal") val tanggal: String,
    @SerializedName("total_harga") val totalHarga: Int,
    @SerializedName("metode_pembayaran") val metodePembayaran: String,
    @SerializedName("status_pesanan") val statusPesanan: String,
    @SerializedName("keterangan") val keterangan: String,
    @SerializedName("id_produk") val idProduk: Int,
    @SerializedName("jumlah_keluar") val jumlahKeluar: Int,
    @SerializedName("subtotal") val subtotal: Int,
    @SerializedName("nama_produk") val namaProduk: String,
    @SerializedName("harga") val harga: Int
)

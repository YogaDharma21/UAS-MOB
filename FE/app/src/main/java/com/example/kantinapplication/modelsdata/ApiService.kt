package com.example.kantinapplication.access

import com.example.kantinapplication.modelsdata.ResponseDashboard
import com.example.kantinapplication.modelsdata.ResponseKonfirmasi
import com.example.kantinapplication.modelsdata.ResponseOrder
import com.example.kantinapplication.modelsdata.ResponseProduct
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("index.php?resource=inventory&apicall=loadData")
    suspend fun getProducts(): Response<ResponseProduct>

    @FormUrlEncoded
    @POST("index.php?resource=inventory&apicall=insertData")
    suspend fun insertProduct(
        @Field("nama_produk") namaProduk: String,
        @Field("harga") harga: String,
        @Field("stok") stok: String
    ): Response<ResponseKonfirmasi>

    @FormUrlEncoded
    @POST("index.php?resource=inventory&apicall=updateData")
    suspend fun updateProduct(
        @Field("id_produk") idProduk: Int,
        @Field("nama_produk") namaProduk: String,
        @Field("harga") harga: String,
        @Field("stok") stok: String
    ): Response<ResponseKonfirmasi>

    @POST("index.php?resource=pesanan&apicall=loadData")
    suspend fun getOrders(): Response<ResponseOrder>

    @FormUrlEncoded
    @POST("index.php?resource=pesanan&apicall=insertData")
    suspend fun insertOrder(
        @Field("no_nota")
        noNota: String,
        @Field("metode_pembayaran")
        metodePembayaran: String,
        @Field("keterangan")
        keterangan: String,
        @Field("id_produk")
        idProduk: Int,
        @Field("jumlah")
        jumlah: Int
    ): Response<ResponseKonfirmasi>

    @FormUrlEncoded
    @POST("index.php?resource=pesanan&apicall=updateData")
    suspend fun updateOrder(
        @Field("id_transaksi")
        idTransaksi: Int
    ): Response<ResponseKonfirmasi>

    @POST("index.php?resource=dashboard&apicall=loadData")
    suspend fun getDashboard(): Response<ResponseDashboard>
}
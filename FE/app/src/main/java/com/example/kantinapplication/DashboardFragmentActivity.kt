package com.example.kantinapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kantinapplication.access.CRUDDashboard
import com.example.kantinapplication.modelsdata.DashboardCallback
import com.example.kantinapplication.modelsdata.DashboardItem

class DashboardFragmentActivity : Fragment(), DashboardCallback{
    private lateinit var crudDashboard: CRUDDashboard
    private lateinit var tvPendapatanHariIni: TextView
    private lateinit var tvJumlahBarangMauHabis: TextView
    private lateinit var tvNamaTerlaris1: TextView
    private lateinit var tvNamaTerlaris2: TextView
    private lateinit var tvNamaTerlaris3: TextView
    private lateinit var tvJumlahTerlaris1: TextView
    private lateinit var tvJumlahTerlaris2: TextView
    private lateinit var tvJumlahTerlaris3: TextView
    private lateinit var tvNamaStokMenipis1: TextView
    private lateinit var tvNamaStokMenipis2: TextView
    private lateinit var tvQtyStokMenipis1: TextView
    private lateinit var tvQtyStokMenipis2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvPendapatanHariIni = view.findViewById(R.id.netTodayTextView)
        tvJumlahBarangMauHabis = view.findViewById(R.id.lowstockCountTextView)

        tvNamaTerlaris1 = view.findViewById(R.id.topSoldName1TextView)
        tvNamaTerlaris2 = view.findViewById(R.id.topSoldName2TextView)
        tvNamaTerlaris3 = view.findViewById(R.id.topSoldName3TextView)
        tvJumlahTerlaris1 = view.findViewById(R.id.topSoldCount1TextView)
        tvJumlahTerlaris2 = view.findViewById(R.id.topSoldCount2TextView)
        tvJumlahTerlaris3 = view.findViewById(R.id.topSoldCount3TextView)

        tvNamaStokMenipis1 = view.findViewById(R.id.lowstockName1TextView)
        tvNamaStokMenipis2 = view.findViewById(R.id.lowStockName2TextView)
        tvQtyStokMenipis1 = view.findViewById(R.id.lowstockQty1TextView)
        tvQtyStokMenipis2 = view.findViewById(R.id.lowstockQty2TextView)

        crudDashboard = CRUDDashboard( requireActivity(), this)
        crudDashboard.getDashboardData()

    }

    override fun onLoadDashboard(data: DashboardItem) {
        activity?.runOnUiThread {
            val rupiah =
                java.text.NumberFormat.getCurrencyInstance(
                    java.util.Locale("in","ID")
                )

            tvPendapatanHariIni.text =
                rupiah.format(data.pendapatanHariIni)

            if (data.produkTerlaris.isNotEmpty()) {
                tvNamaTerlaris1.text = data.produkTerlaris.getOrNull(0)?.namaProduk ?: "-"
                tvJumlahTerlaris1.text = data.produkTerlaris.getOrNull(0)?.jumlahTerjual?.toString() ?: "0"

                tvNamaTerlaris2.text = data.produkTerlaris.getOrNull(1)?.namaProduk ?: "-"
                tvJumlahTerlaris2.text = data.produkTerlaris.getOrNull(1)?.jumlahTerjual?.toString() ?: "0"

                tvNamaTerlaris3.text = data.produkTerlaris.getOrNull(2)?.namaProduk ?: "-"
                tvJumlahTerlaris3.text = data.produkTerlaris.getOrNull(2)?.jumlahTerjual?.toString() ?: "0"
            }

            if (data.stokMenipis.isNotEmpty()) {
                tvNamaStokMenipis1.text = data.stokMenipis.getOrNull(0)?.namaProduk ?: "-"
                tvQtyStokMenipis1.text = data.stokMenipis.getOrNull(0)?.stok?.toString() ?: "0"

                tvNamaStokMenipis2.text = data.stokMenipis.getOrNull(1)?.namaProduk ?: "-"
                tvQtyStokMenipis2.text = data.stokMenipis.getOrNull(1)?.stok?.toString() ?: "0"
            }
        }
    }

    override fun onError(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(context, "Gagal memuat dashboard: $message", Toast.LENGTH_SHORT).show()
        }
    }
}
package com.example.kantinapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kantinapplication.R
import com.example.kantinapplication.modelsdata.OrderItem

class OrderAdapter(
    private val orderList: ArrayList<OrderItem>,
    private val callback: ListenerCallback
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    interface ListenerCallback {
        fun onUpdateData(data: OrderItem)
    }

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textNota: TextView = view.findViewById(R.id.orderIdTextView)
        val textDetail: TextView = view.findViewById(R.id.orderDetailsTextView)
        val btnDone: Button = view.findViewById(R.id.markdoneActionButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        order.let {
            holder.textNota.text = order.noNota
            holder.textDetail.text =
                "${order.jumlahKeluar}x ${order.namaProduk}\n" +
                        "Rp ${order.totalHarga}\n" +
                        order.statusPesanan

            if (order.statusPesanan == "Selesai") {
                holder.btnDone.isEnabled = false
                holder.btnDone.setOnClickListener(null)
            } else {
                holder.btnDone.isEnabled = true
                holder.btnDone.setOnClickListener {
                    callback.onUpdateData(order)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}

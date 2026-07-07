package com.example.kantinapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kantinapplication.R
import com.example.kantinapplication.modelsdata.ProductItem

class InventoryAdapter(
    private val productList: ArrayList<ProductItem>,
    private val callback: ListenerCallback
) : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {
    interface ListenerCallback {
        fun onUpdateData(data: ProductItem)
    }
    class InventoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.productNameTextView)
        val textPrice: TextView = view.findViewById(R.id.productPriceTextView)
        val textStock: TextView = view.findViewById(R.id.productStockTextView)
        val btnEdit: Button = view.findViewById(R.id.editStockActionButton)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory, parent, false)
        return InventoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val product = productList[position]
        product.let {
            holder.textName.text = product.namaProduk
            holder.textPrice.text = product.harga.toString()
            holder.textStock.text = product.stok.toString()

            holder.btnEdit.setOnClickListener {
                callback.onUpdateData(product)
            }
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}

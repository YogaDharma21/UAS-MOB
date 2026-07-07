package com.example.kantinapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kantinapplication.adapter.InventoryAdapter
import com.example.kantinapplication.access.CRUDInventory
import com.example.kantinapplication.modelsdata.InventoryCallback
import com.example.kantinapplication.modelsdata.ProductItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList

class InventoryFragmentActivity : Fragment(),
    InventoryCallback, InventoryAdapter.ListenerCallback {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InventoryAdapter
    private var productList = ArrayList<ProductItem>()
    private lateinit var crudInventory: CRUDInventory
    private var currentDialog: AlertDialog? = null
    private var Mode: String = "Insert"
    private var selectedIdProduk: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_inventory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.inventoryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = InventoryAdapter(productList, this)
        recyclerView.adapter = adapter
        crudInventory = CRUDInventory(requireActivity() as ComponentActivity, this)
        crudInventory.getAllInventory()

        view.findViewById<FloatingActionButton>(R.id.addInventoryFloatingActionButton).setOnClickListener {
            Mode = "Insert"
            tampilkanDialogForm(null)
        }
    }

    private fun tampilkanDialogForm(produk: ProductItem?) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_edit_inventory, null)
        currentDialog = AlertDialog.Builder(context).setView(dialogView).create()

        val titleTv = dialogView.findViewById<TextView>(R.id.titleTextView6)
        val nameInput = dialogView.findViewById<EditText>(R.id.inventoryNameEditText)
        val priceInput = dialogView.findViewById<EditText>(R.id.inventoryPriceEditText)
        val stockInput = dialogView.findViewById<EditText>(R.id.inventoryStockEditText)
        val btnSave = dialogView.findViewById<Button>(R.id.inventorySaveActionButton)

        if (Mode == "Update" && produk != null) {
            titleTv.text = "EDIT PRODUK"
            nameInput.setText(produk.namaProduk)
            priceInput.setText(produk.harga.toString())
            stockInput.setText(produk.stok.toString())
        } else {
            titleTv.text = "TAMBAH PRODUK BARU"
        }

        btnSave.setOnClickListener {
            val nama = nameInput.text.toString().trim()
            val harga = priceInput.text.toString().trim()
            val stok = stockInput.text.toString().trim()

            if (nama.isNotEmpty() && harga.isNotEmpty() && stok.isNotEmpty()) {
                if (Mode == "Insert") {
                    crudInventory.insertInventory(nama, harga, stok)
                } else {
                    crudInventory.updateInventory(selectedIdProduk, nama, harga, stok)
                }
                currentDialog?.dismiss()
                currentDialog = null
            } else {
                Toast.makeText(context, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
            }
        }
        currentDialog?.show()
    }


    override fun onUpdateData(data: ProductItem) {
        Mode = "Update"
        selectedIdProduk = data.idProduk
        tampilkanDialogForm(data)
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onLoadInventory(data: List<ProductItem>) {
        activity?.runOnUiThread {
            productList.clear()
            productList.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onInsertSuccess(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(context, "Berhasil Menambahkan Data!", Toast.LENGTH_SHORT).show()
            currentDialog?.dismiss()
            crudInventory.getAllInventory()
        }
    }

    override fun onUpdateSuccess(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(context, "Berhasil Memperbarui Data!", Toast.LENGTH_SHORT).show()
            currentDialog?.dismiss()
            crudInventory.getAllInventory()
        }
    }

    override fun onError(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
package com.example.kantinapplication

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kantinapplication.access.CRUDInventory
import com.example.kantinapplication.access.CRUDOrders
import com.example.kantinapplication.adapter.OrderAdapter
import com.example.kantinapplication.modelsdata.InventoryCallback
import com.example.kantinapplication.modelsdata.OrderItem
import com.example.kantinapplication.modelsdata.OrdersCallback
import com.example.kantinapplication.modelsdata.ProductItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class OrdersFragmentActivity : Fragment(), OrdersCallback,
    OrderAdapter.ListenerCallback {
    private lateinit var crudOrders: CRUDOrders
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderAdapter
    private lateinit var crudInventory: CRUDInventory
    private var productData = listOf<ProductItem>()
    private val orderList = ArrayList<OrderItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.ordersRecyclerView)
        adapter = OrderAdapter(orderList, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        crudOrders = CRUDOrders(requireActivity(), this)
        crudOrders.getAllOrders()

        crudInventory = CRUDInventory(
            requireActivity(),
            object : InventoryCallback {

                override fun onLoadInventory(data: List<ProductItem>) {}
                override fun onInsertSuccess(message: String) {}
                override fun onUpdateSuccess(message: String) {}
                override fun onError(message: String) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        )

        // Starts here
        view.findViewById<FloatingActionButton>(R.id.addOrderFloatingActionButton).setOnClickListener {
            val dialog = LayoutInflater.from(context).inflate(R.layout.dialog_add_order, null)
            val builder = AlertDialog.Builder(context).setView(dialog).create()
            val productSpinner = dialog.findViewById<Spinner>(R.id.productSpinner)
            dialog.findViewById<Button>(R.id.orderSaveActionButton).setOnClickListener {
                // TODO: insert ke database dan tampilkan ke ordersRecyclerView
                val orderIdEditText = dialog.findViewById<EditText>(R.id.orderIdEditText)
                val quantityEditText = dialog.findViewById<EditText>(R.id.quantityEditText)
                val paymentSpinner = dialog.findViewById<Spinner>(R.id.paymentSpinner)
                val notesEditText = dialog.findViewById<EditText>(R.id.notesEditText)

                val noNota = orderIdEditText.text.toString().trim()
                val jumlah = quantityEditText.text.toString().toIntOrNull() ?: 0
                val metodePembayaran = paymentSpinner.selectedItem.toString()
                val keterangan = notesEditText.text.toString().trim()

                if (noNota.isEmpty() ||keterangan.isEmpty() || jumlah <= 0) {
                    Toast.makeText(
                        requireContext(),
                        "Lengkapi data terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()

                } else if (productData.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Produk belum tersedia",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    val selectedProduct = productData[productSpinner.selectedItemPosition]
                    val idProduk = selectedProduct.idProduk

                    crudOrders.insertOrder(
                        noNota,
                        metodePembayaran,
                        keterangan,
                        idProduk,
                        jumlah
                    )

                    builder.dismiss()
                }
            }

            // TODO: populasikan data dari db ke spinner
            // Example

            crudInventory.getAllInventoryForSpinner { products ->
                productData = products
                val listMenuKantin = products.map {
                    "${it.namaProduk} - Rp${it.harga}"
                }

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    listMenuKantin
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                productSpinner.adapter = adapter
            }

            builder.show()
        }
    }

    override fun onLoadOrders(data: List<OrderItem>) {
        orderList.clear()
        orderList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onInsertSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        crudOrders.getAllOrders()
    }

    override fun onUpdateSuccess(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        crudOrders.getAllOrders()
    }

    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateData(data: OrderItem) {
        crudOrders.updateOrder(data.idTransaksi)
    }
}
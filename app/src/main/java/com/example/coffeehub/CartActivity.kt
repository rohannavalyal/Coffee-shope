package com.example.coffeehub

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeehub.adapter.CartAdapter

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val cartRecyclerView = findViewById<RecyclerView>(R.id.cartRecyclerView)
        val subtotalText = findViewById<TextView>(R.id.subtotalText)
        val deliveryText = findViewById<TextView>(R.id.deliveryText)
        val taxText = findViewById<TextView>(R.id.taxText)
        val totalText = findViewById<TextView>(R.id.totalText)

        val cartItems = CartManager.cartItems
        if (cartItems.isEmpty()) {
            subtotalText.text = "Your cart is empty."
            deliveryText.text = ""
            taxText.text = ""
            totalText.text = ""
            cartRecyclerView.visibility = View.GONE
        } else {
            cartRecyclerView.visibility = View.VISIBLE
            cartRecyclerView.layoutManager = LinearLayoutManager(this)
            
            // Convert List<Pair<Coffee, Int>> to List<OrderItem> for the adapter
            val orderItemList = cartItems.map { (coffee, quantity) ->
                OrderItem(coffee, quantity)
            }

            cartRecyclerView.adapter = CartAdapter(orderItemList)
            
            val subtotal = cartItems.sumOf { (coffee, qty) -> coffee.price * qty }.toDouble()
            val delivery = 15.0
            val tax = subtotal * 0.02
            val total = subtotal + delivery + tax
            subtotalText.text = "Subtotal ₹${subtotal}"
            deliveryText.text = "Delivery ₹${delivery}"
            taxText.text = "Total Tax ₹${"%.2f".format(tax)}"
            totalText.text = "Total ₹${"%.2f".format(total)}"
        }
    }
} 
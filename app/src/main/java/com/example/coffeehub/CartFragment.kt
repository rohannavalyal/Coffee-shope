package com.example.coffeehub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeehub.adapter.CartAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private var cartRecyclerView: RecyclerView? = null
    private var subtotalText: TextView? = null
    private var deliveryText: TextView? = null
    private var taxText: TextView? = null
    private var totalText: TextView? = null
    private var checkoutButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        cartRecyclerView = view.findViewById(R.id.cartRecyclerView)
        subtotalText = view.findViewById(R.id.subtotalText)
        deliveryText = view.findViewById(R.id.deliveryText)
        taxText = view.findViewById(R.id.taxText)
        totalText = view.findViewById(R.id.totalText)
        checkoutButton = view.findViewById(R.id.checkoutButton)

        updateCartUI()

        return view
    }

    override fun onResume() {
        super.onResume()
        // Update cart UI when fragment becomes visible
        updateCartUI()
    }

    private fun updateCartUI() {
         val cartItems = CartManager.cartItems
        if (cartItems.isEmpty()) {
            subtotalText?.text = "Your cart is empty"
            deliveryText?.text = ""
            taxText?.text = ""
            totalText?.text = ""
            cartRecyclerView?.visibility = View.GONE
            checkoutButton?.isEnabled = false
        } else {
            cartRecyclerView?.visibility = View.VISIBLE
            cartRecyclerView?.layoutManager = LinearLayoutManager(context)
            
            // Convert List<Pair<Coffee, Int>> to List<OrderItem> for the adapter
            val orderItemList = cartItems.map { (coffee, quantity) ->
                OrderItem(coffee, quantity)
            }
            
            cartRecyclerView?.adapter = CartAdapter(orderItemList)
            
            val subtotal = cartItems.sumOf { (coffee, qty) -> coffee.price * qty }.toDouble()
            val delivery = 15.0
            val tax = subtotal * 0.02
            val total = subtotal + delivery + tax
            
            subtotalText?.text = "Subtotal ₹$subtotal"
            deliveryText?.text = "Delivery ₹$delivery"
            taxText?.text = "Total Tax ₹${"%.2f".format(tax)}"
            totalText?.text = "Total ₹${"%.2f".format(total)}"
            checkoutButton?.isEnabled = true

            checkoutButton?.setOnClickListener {
                val currentUser = auth.currentUser
                if (currentUser == null) {
                    Toast.makeText(context, "Please sign in to place an order", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Convert List<Pair<Coffee, Int>> to List<OrderItem>
                val orderItems = cartItems.map { (coffee, quantity) ->
                    OrderItem(coffee, quantity)
                }

                val order = Order(
                    userId = currentUser.uid,
                    items = orderItems,
                    subtotal = subtotal,
                    delivery = delivery,
                    tax = tax,
                    total = total
                )

                // Add order to Firestore
                db.collection("orders")
                    .add(order)
                    .addOnSuccessListener { documentReference ->
                        // Show order confirmation
                        val orderId = documentReference.id
                        val bundle = Bundle().apply {
                            putString("orderId", orderId)
                            putDouble("orderTotal", total)
                        }
                        
                        val orderConfirmationFragment = OrderConfirmationFragment().apply {
                            arguments = bundle
                        }
                        
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, orderConfirmationFragment)
                            .commit()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Error placing order: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear references to views when the view is destroyed
        cartRecyclerView = null
        subtotalText = null
        deliveryText = null
        taxText = null
        totalText = null
        checkoutButton = null
    }
} 
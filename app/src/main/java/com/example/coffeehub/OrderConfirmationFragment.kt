package com.example.coffeehub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class OrderConfirmationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_confirmation, container, false)

        val orderIdText = view.findViewById<TextView>(R.id.orderIdText)
        val orderStatusText = view.findViewById<TextView>(R.id.orderStatusText)
        val orderTotalText = view.findViewById<TextView>(R.id.orderTotalText)
        val backToHomeButton = view.findViewById<Button>(R.id.backToHomeButton)

        // Get order details from arguments
        val orderId = arguments?.getString("orderId") ?: ""
        val orderTotal = arguments?.getDouble("orderTotal") ?: 0.0

        orderIdText.text = "Order #$orderId"
        orderStatusText.text = "Order Confirmed"
        orderTotalText.text = "Total: ₹${String.format("%.2f", orderTotal)}"

        backToHomeButton.setOnClickListener {
            // Clear cart
            CartManager.cartItems.clear()
            // Navigate back to home
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        }

        return view
    }
} 
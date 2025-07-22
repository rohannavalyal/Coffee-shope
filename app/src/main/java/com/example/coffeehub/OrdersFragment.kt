package com.example.coffeehub

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class OrdersFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private var ordersRecyclerView: RecyclerView? = null
    private var emptyText: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        ordersRecyclerView = view.findViewById(R.id.ordersRecyclerView)
        emptyText = view.findViewById(R.id.emptyText)

        ordersRecyclerView?.layoutManager = LinearLayoutManager(context)

        val currentUser = auth.currentUser
        if (currentUser == null) {
            emptyText?.text = "Please sign in to view orders"
            emptyText?.visibility = View.VISIBLE
            ordersRecyclerView?.visibility = View.GONE
        } else {
            // Query orders for current user, ordered by timestamp descending
            db.collection("orders")
                .whereEqualTo("userId", currentUser.uid)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { documents ->
                    // Check if the fragment is still added and its view is not null before updating UI
                    if (isAdded && view != null) {
                        if (documents.isEmpty) {
                            emptyText?.text = "No orders yet"
                            emptyText?.visibility = View.VISIBLE
                            ordersRecyclerView?.visibility = View.GONE
                        } else {
                            emptyText?.visibility = View.GONE
                            ordersRecyclerView?.visibility = View.VISIBLE
                            
                            val orders = documents.mapNotNull { doc ->
                                doc.toObject(Order::class.java)
                            }
                            
                            // Create and set adapter
                            val adapter = OrdersAdapter(orders)
                            ordersRecyclerView?.adapter = adapter
                        }
                    }
                }
                .addOnFailureListener { e ->
                     // Check if the fragment is still added and its view is not null before updating UI
                    if (isAdded && view != null) {
                        emptyText?.text = "Error loading orders: ${e.message}"
                        emptyText?.visibility = View.VISIBLE
                        ordersRecyclerView?.visibility = View.GONE
                    }
                }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear references to views when the view is destroyed
        ordersRecyclerView = null
        emptyText = null
    }
}

class OrdersAdapter(private val orders: List<Order>) : 
    RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderIdText: TextView = view.findViewById(R.id.orderIdText)
        val orderDateText: TextView = view.findViewById(R.id.orderDateText)
        val orderTotalText: TextView = view.findViewById(R.id.orderTotalText)
        val orderStatusText: TextView = view.findViewById(R.id.orderStatusText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        
        holder.orderIdText.text = "Order #${order.id.take(8)}"
        holder.orderDateText.text = dateFormat.format(order.timestamp.toDate())
        holder.orderTotalText.text = "₹${String.format("%.2f", order.total)}"
        holder.orderStatusText.text = order.status
    }

    override fun getItemCount() = orders.size
} 
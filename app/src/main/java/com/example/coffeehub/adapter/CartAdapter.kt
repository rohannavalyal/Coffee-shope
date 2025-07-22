package com.example.coffeehub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeehub.Coffee
import com.example.coffeehub.OrderItem
import com.example.coffeehub.R

class CartAdapter(private val cartItems: List<OrderItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.cartItemImage)
        val nameTextView: TextView = itemView.findViewById(R.id.cartItemName)
        val priceTextView: TextView = itemView.findViewById(R.id.cartItemPrice)
        val quantityTextView: TextView = itemView.findViewById(R.id.cartItemQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val orderItem = cartItems[position]
        val coffee = orderItem.coffee

        if (coffee != null) {
            Glide.with(holder.itemView.context)
                .load(coffee.imageUrl)
                .into(holder.imageView)
            holder.nameTextView.text = coffee.name
            holder.priceTextView.text = "₹${coffee.price}"
            holder.quantityTextView.text = "Quantity: ${orderItem.quantity}"
        } else {
            holder.nameTextView.text = "Invalid Item"
            holder.priceTextView.text = ""
            holder.quantityTextView.text = ""
            holder.imageView.setImageResource(android.R.drawable.ic_dialog_alert)
        }
    }

    override fun getItemCount() = cartItems.size
} 
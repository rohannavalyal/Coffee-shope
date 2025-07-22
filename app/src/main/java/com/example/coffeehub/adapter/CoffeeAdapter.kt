package com.example.coffeehub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeehub.Coffee
import com.example.coffeehub.R
import android.util.Log
import android.widget.Toast

class CoffeeAdapter(
    private var coffeeList: List<Coffee>,
    private val onItemClick: (Coffee) -> Unit,
    private val onAddToCartClick: (Coffee, Int) -> Unit
) : RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

    private val quantities = MutableList(coffeeList.size) { 0 }

    class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.coffeeImage)
        val nameTextView: TextView = itemView.findViewById(R.id.coffeeName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.coffeeDescription)
        val priceTextView: TextView = itemView.findViewById(R.id.coffeePrice)
        val addToCartButton: Button = itemView.findViewById(R.id.addToCartButton)
        val plusButton: Button = itemView.findViewById(R.id.plusButton)
        val minusButton: Button = itemView.findViewById(R.id.minusButton)
        val quantityText: TextView = itemView.findViewById(R.id.quantityText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coffee, parent, false)
        return CoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val coffee = coffeeList[position]
        
        // Load image using Glide from imageUrl
        Glide.with(holder.itemView.context)
             .load(coffee.imageUrl)
             .placeholder(android.R.drawable.ic_menu_gallery) // Optional placeholder
             .error(android.R.drawable.ic_dialog_alert) // Optional error image
             .into(holder.imageView)

        holder.nameTextView.text = coffee.name
        holder.descriptionTextView.text = coffee.desc // Use 'desc'
        holder.priceTextView.text = "₹${coffee.price}"

        // Set click listeners
        holder.itemView.setOnClickListener { onItemClick(coffee) }
        
        // Ensure quantities list is correctly sized for the current coffeeList
        if (quantities.size <= position) {
            // This case should ideally not happen if updateData is called correctly,
            // but as a safeguard, add a default quantity if needed.
            while(quantities.size <= position) quantities.add(0)
        }
        holder.quantityText.text = quantities[position].toString()
        updateAddToCartButton(holder, position)

        holder.plusButton.setOnClickListener {
             if (quantities.size > position && quantities[position] < 10) { // Add bounds check
                quantities[position]++
                holder.quantityText.text = quantities[position].toString()
                updateAddToCartButton(holder, position)
            }
        }

        holder.minusButton.setOnClickListener {
            if (quantities.size > position && quantities[position] > 0) { // Add bounds check
                quantities[position]--
                holder.quantityText.text = quantities[position].toString()
                updateAddToCartButton(holder, position)
            }
        }

        holder.addToCartButton.setOnClickListener {
            if (quantities.size > position && quantities[position] > 0) { // Add bounds check
                onAddToCartClick(coffee, quantities[position])
            }
        }
    }

    private fun updateAddToCartButton(holder: CoffeeViewHolder, position: Int) {
         if (quantities.size > position) { // Add bounds check before accessing quantities[position]
            if (quantities[position] == 0) {
                holder.addToCartButton.isEnabled = false
                holder.addToCartButton.alpha = 0.5f
            } else {
                holder.addToCartButton.isEnabled = true
                holder.addToCartButton.alpha = 1.0f
            }
         } else {
             // Handle case where quantities list is not in sync (shouldn't happen with correct updateData)
             holder.addToCartButton.isEnabled = false
             holder.addToCartButton.alpha = 0.5f
         }
    }

    override fun getItemCount() = coffeeList.size

    fun updateData(newList: List<Coffee>) {
        coffeeList = newList
        quantities.clear()
        repeat(newList.size) { quantities.add(0) }
        notifyDataSetChanged()
    }
}

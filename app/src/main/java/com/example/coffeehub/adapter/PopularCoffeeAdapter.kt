package com.example.coffeehub.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeehub.Coffee
import com.example.coffeehub.R

class PopularCoffeeAdapter(
    private val coffeeList: List<Coffee>,
    private val onItemClick: (Coffee) -> Unit
) : RecyclerView.Adapter<PopularCoffeeAdapter.PopularCoffeeViewHolder>() {

    class PopularCoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.popularCoffeeImage)
        val nameTextView: TextView = itemView.findViewById(R.id.popularCoffeeName)
        val priceTextView: TextView = itemView.findViewById(R.id.popularCoffeePrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCoffeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_coffee, parent, false)
        return PopularCoffeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularCoffeeViewHolder, position: Int) {
        val coffee = coffeeList[position]
        Glide.with(holder.itemView.context)
            .load(coffee.imageUrl)
            .into(holder.imageView)
        holder.nameTextView.text = coffee.name
        holder.priceTextView.text = "₹${coffee.price}"
        
        holder.itemView.setOnClickListener { onItemClick(coffee) }
    }

    override fun getItemCount() = coffeeList.size
} 
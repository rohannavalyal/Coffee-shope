package com.example.coffeehub

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CoffeeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_details)

        val name = intent.getStringExtra("name") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val price = intent.getIntExtra("price", 0)
        val imageUrl = intent.getStringExtra("imageUrl")

        findViewById<TextView>(R.id.detailsName).text = name
        findViewById<TextView>(R.id.detailsDescription).text = description
        findViewById<TextView>(R.id.detailsPrice).text = "₹$price"

        val detailsImage = findViewById<ImageView>(R.id.detailsImage)
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this)
                 .load(imageUrl)
                 .placeholder(android.R.drawable.ic_menu_gallery)
                 .error(android.R.drawable.ic_dialog_alert)
                 .into(detailsImage)
        } else {
            detailsImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }
} 
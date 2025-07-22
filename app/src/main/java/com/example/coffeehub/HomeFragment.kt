package com.example.coffeehub

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeehub.adapter.CoffeeAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var coffeeRecyclerView: RecyclerView
    private lateinit var coffeeAdapter: CoffeeAdapter
    private val coffeeList = mutableListOf<Coffee>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Set up coffee grid (initialize adapter with an empty list initially)
        coffeeRecyclerView = view.findViewById(R.id.coffeeRecyclerView)
        coffeeRecyclerView.layoutManager = GridLayoutManager(context, 2)

        // Pass empty list initially. Data will be loaded from Firestore.
        coffeeAdapter = CoffeeAdapter(
            coffeeList = mutableListOf(), // Provide an empty list initially
            onItemClick = { coffee ->
                val intent = Intent(activity, CoffeeDetailsActivity::class.java)
                intent.putExtra("name", coffee.name)
                intent.putExtra("description", coffee.desc)
                intent.putExtra("price", coffee.price)
                intent.putExtra("imageUrl", coffee.imageUrl)
                startActivity(intent)
            },
            onAddToCartClick = { coffee, quantity ->
                // Handle add to cart click (implementation will come later with Firestore)
                CartManager.cartItems.add(Pair(coffee, quantity))
                Toast.makeText(context, "Added ${quantity} of ${coffee.name} to cart", Toast.LENGTH_SHORT).show()
            }
        )
        coffeeRecyclerView.adapter = coffeeAdapter

        // Load hero image using Glide
        val heroImage = view.findViewById<ImageView>(R.id.heroImage)
        Glide.with(this)
            .load("https://i.pinimg.com/736x/74/b0/86/74b08647571436ae075cacfaf9c5a43f.jpg")
            .into(heroImage)

        // Fetch coffee data from Firestore
        fetchCoffeeData()

        return view
    }

    private fun fetchCoffeeData() {
        firestore.collection("coffees")
            .get()
            .addOnSuccessListener { result ->
                coffeeList.clear()
                for (document in result) {
                    try {
                        val coffee = document.toObject(Coffee::class.java)
                        coffeeList.add(coffee)
                    } catch (e: Exception) {
                        Log.e("HomeFragment", "Error converting document to Coffee", e)
                    }
                }
                // Update the adapter with the fetched data
                coffeeAdapter.updateData(coffeeList)
            }
            .addOnFailureListener { exception ->
                Log.w("HomeFragment", "Error getting documents: ", exception)
                Toast.makeText(context, "Error loading coffees: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
} 
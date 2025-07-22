package com.example.coffeehub

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class OrderItem(
    var coffee: Coffee? = null,
    var quantity: Int = 0
)

data class Order(
    @DocumentId
    val id: String = "",
    val userId: String = "",
    val items: List<OrderItem> = listOf(),
    val subtotal: Double = 0.0,
    val delivery: Double = 15.0,
    val tax: Double = 0.0,
    val total: Double = 0.0,
    val status: String = "Pending",
    val timestamp: Timestamp = Timestamp.now()
) 
package com.example.coffeehub

import com.google.firebase.firestore.DocumentId

data class Coffee(
    @DocumentId val id: String? = null,
    val name: String = "",
    val desc: String = "",
    val imageUrl: String = "",
    val price: Int = 0
) {
    constructor() : this("")
}

package com.infy.infystore


 data class DummyProduct(
    val categories: List<Category>
)

 data class Category(
    val imageUrl: String,
    val title: String
)
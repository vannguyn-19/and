package com.example.book.model

data class Book(
    val id: Int = 0,
    val title: String,
    val author: String,
    val description: String,
    val price: Double
)
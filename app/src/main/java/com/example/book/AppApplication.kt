package com.example.book

import android.app.Application
import com.example.book.network.RetrofitClient
import com.example.book.repository.BookRepository

class AppApplication : Application() {
    val repository: BookRepository by lazy {
        val retrofitClient = RetrofitClient()
        BookRepository(retrofitClient)
    }

    override fun onCreate() {
        super.onCreate()
    }
}
package com.example.book

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.book.model.Book
import com.example.book.viewmodel.BookViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val app = application as AppApplication

        val repository = app.repository
        viewModel = BookViewModel(repository)

        val etTitle = findViewById<EditText>(R.id.edTitle)
        val etAuthor = findViewById<EditText>(R.id.edActhor)
        val etDescription = findViewById<EditText>(R.id.edDescribe)
        val etPrice = findViewById<EditText>(R.id.edPrice)
        val cbFavorite = findViewById<CheckBox>(R.id.cbFavorite)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnCancel = findViewById<Button>(R.id.btnCancel)

        btnSave.setOnClickListener {
            val title = etTitle.text.toString()
            val author = etAuthor.text.toString()
            val desc = etDescription.text.toString()
            val price = etPrice.text.toString().toDoubleOrNull() ?: 0.0
            val favorite = cbFavorite.isChecked

            if (title.isNotEmpty() && author.isNotEmpty()) {
                val book = Book(title = title, author = author, description = desc, price = price)
                lifecycleScope.launch {
                    try {
                        viewModel.addBook(book)
                        startActivity(Intent(this@MainActivity, SecondActivity::class.java))
                        finish()
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Error adding book: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        btnCancel.setOnClickListener {
            etTitle.text.clear()
            etAuthor.text.clear()
            etDescription.text.clear()
            etPrice.text.clear()
            cbFavorite.isChecked = false
        }
    }
}
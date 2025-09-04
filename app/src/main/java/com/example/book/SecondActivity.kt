package com.example.book

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.book.adapter.BookAdapter
import com.example.book.model.Book
import com.example.book.viewmodel.BookViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {

    private lateinit var adapter: BookAdapter
    private lateinit var viewModel: BookViewModel
    private val selectedBooks = mutableListOf<Book>()

    private var isSelectionMode = false
    private lateinit var btnBack: Button
    private lateinit var btnDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        adapter = BookAdapter(
            onItemSelected = { book, isChecked ->
                if (isChecked) {
                    selectedBooks.add(book)
                } else {
                    selectedBooks.remove(book)
                }

                adapter.selectedBooks = selectedBooks.toList()
                adapter.notifyDataSetChanged()
            }
        )
        val app = application as? AppApplication
        if (app == null) {
            throw IllegalStateException("AppApplication not found. Check AndroidManifest.xml and package name.")
        }
        val repository = app.repository
        viewModel = BookViewModel(repository)

        val rvBooks = findViewById<RecyclerView>(R.id.recyclerView)
        btnBack = findViewById(R.id.btnBack)
        btnDelete = findViewById(R.id.btnDelete)

        rvBooks.adapter = adapter
        rvBooks.layoutManager = LinearLayoutManager(this)

        btnBack.setOnClickListener {
            startActivity(Intent(this@SecondActivity, MainActivity::class.java))
            finish()
        }

        btnDelete.setOnClickListener {
            deleteSelectedBooks()
        }

        viewModel.refreshBooks()
        lifecycleScope.launch {
            viewModel.books.collectLatest { books ->
                adapter.submitList(books)
            }
        }
    }

    private fun deleteSelectedBooks() {
        if (selectedBooks.isNotEmpty()) {
            lifecycleScope.launch {
                try {
                    selectedBooks.forEach { book ->
                        viewModel.deleteBook(book.id)
                    }
                    selectedBooks.clear()
                } catch (e: Exception) {
                    throw e
                }
            }
        } else {
            Toast.makeText(this@SecondActivity, "Vui lòng chọn sách để xóa", Toast.LENGTH_SHORT).show()
        }
    }
}



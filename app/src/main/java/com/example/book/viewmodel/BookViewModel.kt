package com.example.book.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book.model.Book
import com.example.book.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class BookViewModel(
    private val repository: BookRepository
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    init {
        refreshBooks()
    }

    fun refreshBooks() {
        viewModelScope.launch {
            try {
                repository.getAllBooks().collect { apiBooks ->
                    _books.value = apiBooks
                    Log.d("BookViewModel", "Refreshed books: ${apiBooks.size} items")
                }
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error refreshing books: ${e.message}", e)
                _books.value = emptyList()
            }
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch {
            try {
                repository.addBook(book)
                refreshBooks()
                Log.d("BookViewModel", "Added book: $book")
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error adding book: ${e.message}", e)
            }
        }
    }

    fun deleteBook(bookId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteBook(bookId)
                refreshBooks()
                Log.d("BookViewModel", "Deleted book with ID: $bookId")
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error deleting book: ${e.message}", e)
            }
        }
    }
}
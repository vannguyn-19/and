package com.example.book.repository

import com.example.book.model.Book
import com.example.book.network.RetrofitClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import android.util.Log

class BookRepository(
    private val retrofitClient: RetrofitClient
) {
    fun getAllBooks(): Flow<List<Book>> = flow {
        try {
            val books = retrofitClient.apiService.getAllBooks()
            Log.d("BookRepository", "Fetched ${books.size} books from API")
            emit(books)
        } catch (e: Exception) {
            Log.e("BookRepository", "Error fetching books: ${e.message}", e)
            emit(emptyList())
        }
    }

    suspend fun addBook(book: Book) {
        try {
            retrofitClient.apiService.addBook(book)
            Log.d("BookRepository", "Added book: $book")
        } catch (e: Exception) {
            Log.e("BookRepository", "Error adding book: ${e.message}", e)
            throw e
        }
    }



    suspend fun deleteBook(bookId: Int) {
        try {
            retrofitClient.apiService.deleteBook(bookId)
            Log.d("BookRepository", "Deleted book with ID: $bookId")
        } catch (e: Exception) {
            Log.e("BookRepository", "Error deleting book: ${e.message}", e)
            throw e
        }
    }
}
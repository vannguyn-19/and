package com.example.book.network

import com.example.book.model.Book
import retrofit2.http.*

interface BookApiService {
    @GET("api/Books")
    suspend fun getAllBooks(): List<Book>
    @POST("api/Books")
    @Headers("Content-Type: application/json")
    suspend fun addBook(@Body book: Book): Book
    @DELETE("api/Books/{id}")
    suspend fun deleteBook(@Path("id") id: Int)

}
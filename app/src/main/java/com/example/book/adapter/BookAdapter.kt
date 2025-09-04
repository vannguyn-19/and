package com.example.book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.book.R
import com.example.book.model.Book
import kotlin.random.Random

class BookAdapter(
    private val onItemSelected: (Book, Boolean) -> Unit
) : ListAdapter<Book, BookAdapter.BookViewHolder>(DiffCallback) {
    var selectedBooks: List<Book> = emptyList()

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cbSelect: CheckBox = itemView.findViewById(R.id.cbdelete)
        private val tvTitle: TextView = itemView.findViewById(R.id.tvName)
        private val tvAuthor: TextView = itemView.findViewById(R.id.tvActhor)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescribe)
        private val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        private val ivRandomImage: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(book: Book, selectedBooks: List<Book>) {
            tvTitle.text = book.title
            tvAuthor.text = book.author
            tvDescription.text = book.description
            tvPrice.text = "${book.price}đ"

            val imageResources = intArrayOf(R.drawable.img, R.drawable.img_1, R.drawable.img_2)
            val randomIndex = Random.nextInt(imageResources.size)
            ivRandomImage.setImageResource(imageResources[randomIndex])

            cbSelect.visibility = View.VISIBLE
            cbSelect.isChecked = selectedBooks.contains(book)

            cbSelect.setOnCheckedChangeListener(null)
            cbSelect.setOnCheckedChangeListener { _, isChecked ->
                onItemSelected(book, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.bind(book, selectedBooks)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean = oldItem == newItem
        }
    }
}
package com.yuriy.fragmentexample

data class Item(val title: String, val description: String, val imageRes: Int)

object DataHelper {
    private val booksList = mutableListOf<Item>()

    init {
        createBooksList()
    }

    private fun createBooksList() {
        for (i in 0..100) {
            booksList.add(Item("Book $i", "Book $i  description", R.drawable.ic_image_black_24dp))
        }
    }

    fun getBooksList() : List<Item> = booksList

}

package com.yuriy.fragmentexample

import java.io.InputStream

data class Item(
    val title: String?,
    val link: String?,
    val date: String?
)

object DataHelper {
    const val rssFeedUrl: String = "https://theguardian.com/world/rss"

    fun createNewsListFromRSS(stream: InputStream?): List<Item> {
        if (stream != null) {
            return RssParser().parse(stream)
        } else return listOf(Item("null", "null", "null"))
    }
}



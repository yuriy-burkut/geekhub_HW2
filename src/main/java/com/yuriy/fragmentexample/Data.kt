package com.yuriy.fragmentexample

import android.os.Parcel
import android.os.Parcelable
import java.io.InputStream

data class Item(
    val title: String?,
    val link: String?,
    val date: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(link)
        dest.writeString(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(source: Parcel): Item {
            return Item(source)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}

object DataHelper {
    const val rssFeedUrl: String = "https://theguardian.com/world/rss"

    fun createNewsListFromRSS(stream: InputStream?): List<Item> {
        if (stream != null) {
            return RssParser().parse(stream)
        } else return listOf(Item("null", "null", "null"))
    }
}



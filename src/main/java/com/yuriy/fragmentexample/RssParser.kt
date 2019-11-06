package com.yuriy.fragmentexample

import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

private val ns: String? = null

class RssParser {
    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(stream: InputStream): List<Item> {
        stream.use {
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(stream, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun readFeed(parser: XmlPullParser): List<Item> {
        val items = mutableListOf<Item>()

        parser.require(XmlPullParser.START_TAG, ns, "rss")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            parser.require(XmlPullParser.START_TAG, ns, "channel")
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.eventType != XmlPullParser.START_TAG) {
                    continue
                }
                if (parser.name == "item") {
                    items.add(readItem(parser))
                } else {
                    skip(parser)
                }
            }
        }
        return items
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readItem(parser: XmlPullParser): Item {
        parser.require(XmlPullParser.START_TAG, ns, "item")
        var title: String? = null
        var link: String? = null
        var pubDate: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) continue

            when (parser.name) {
                "title" -> title = read(parser, "title")
                "link" -> link = read(parser, "link")
                "pubDate" -> pubDate = read(parser, "pubDate")
                else -> skip(parser)
            }
        }

        return Item(title, link, pubDate)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun read(parser: XmlPullParser, name: String): String {
        parser.require(XmlPullParser.START_TAG, ns, name)
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, name)
        return title
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) throw IllegalStateException()
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}
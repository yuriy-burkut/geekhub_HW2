package com.yuriy.fragmentexample

import android.app.AlertDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.list_fragment.*
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.net.URL
import java.net.URLConnection

class ListFragment : Fragment(), ItemsAdapter.AdapterCallback {

    interface OnArticleSelectedListener {
        fun onArticleSelected(url: String)
    }

    private lateinit var newsList: List<Item>

    private lateinit var callback: OnArticleSelectedListener

    fun setOnArticleSelectedListener(callback: OnArticleSelectedListener) {
        this.callback = callback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsList = DownloadXmlTask().execute(DataHelper.rssFeedUrl).get() ?: emptyList()
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        items_list.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = ItemsAdapter(newsList, this@ListFragment)
        }
    }

    override fun onItemClick(position: Int) {
        callback.onArticleSelected(newsList[position].link ?: "empty")
    }

    private inner class DownloadXmlTask : AsyncTask<String, Unit, List<Item>>() {

        var exception: String = ""

        override fun doInBackground(vararg urls: String): List<Item> {
            val url = URL(urls[0])
            return try {
                val urlConnection: URLConnection = url.openConnection()
                return DataHelper.createNewsListFromRSS(urlConnection.getInputStream())
            } catch (e: IOException) {
                exception = e.toString()
                emptyList()
            } catch (e: XmlPullParserException) {
                exception = e.toString()
                emptyList()
            }
        }

        override fun onPostExecute(result: List<Item>?) {
            super.onPostExecute(result)

            if (result.isNullOrEmpty()) {
                val dialog = AlertDialog.Builder(requireContext())
                dialog.setMessage(exception).setTitle(R.string.error).create().show()
            }
        }
    }
}

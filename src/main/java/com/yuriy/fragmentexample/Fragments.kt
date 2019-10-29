package com.yuriy.fragmentexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.list_fragment, container, false)
        val rv: RecyclerView = view.findViewById(R.id.items_list)
        rv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = ItemsAdapter(DataHelper.getBooksList())
        }
        return view
    }
}

class ArticleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.article_fragment, container, false)
    }
}

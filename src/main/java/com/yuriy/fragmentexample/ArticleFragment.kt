package com.yuriy.fragmentexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.article_fragment.*

const val ARGS_URL: String = "url"

class ArticleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.article_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            id_web_view.restoreState(savedInstanceState)
        } else {
            val args: Bundle? = arguments

            if (args != null) {
                goToUrl(args.getString(ARGS_URL) ?: "https://www.theguardian.com/us")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        id_web_view.saveState(outState)
    }

    fun goToUrl(url: String) {
        id_web_view.loadUrl(url)
    }
}
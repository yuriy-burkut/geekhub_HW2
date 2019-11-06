package com.yuriy.fragmentexample

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ListFragment.OnArticleSelectedListener {

    private var list = ListFragment()
    private var article = ArticleFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {

            list.setOnArticleSelectedListener(this)
            article.arguments = intent.extras

            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> with(supportFragmentManager) {
                    beginTransaction().add(R.id.id_list_container, list).commit()
                }
                Configuration.ORIENTATION_LANDSCAPE -> with(supportFragmentManager) {
                    beginTransaction().add(R.id.id_list_container, list).commit()
                    beginTransaction()
                        .add(R.id.id_article_container, article)
                        .commit()
                }
            }
        }

        if (savedInstanceState != null) {

            val newList =
                supportFragmentManager.findFragmentById(R.id.id_list_container) as ListFragment?
            val newArticle =
                supportFragmentManager.findFragmentById(R.id.id_article_container) as ArticleFragment?

            list = newList ?: ListFragment()
            list.setOnArticleSelectedListener(this)
            article = newArticle ?: ArticleFragment()
            article.arguments = intent.extras

            when (resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> with(supportFragmentManager) {
                    beginTransaction().replace(R.id.id_article_container, article).commit()
                }
                Configuration.ORIENTATION_PORTRAIT -> with(supportFragmentManager) {
                    beginTransaction().remove(article).commit()
                }
            }
        }
    }

    override fun onArticleSelected(url: String) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            article.goToUrl(url)
        } else {
            val args = Bundle()
            args.putString(ARGS_URL, url)
            article.arguments = args

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, article)
                .addToBackStack(null)
                .commit()
        }
    }
}

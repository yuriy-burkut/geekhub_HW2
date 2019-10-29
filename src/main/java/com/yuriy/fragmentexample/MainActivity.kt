package com.yuriy.fragmentexample

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = ListFragment().apply {
            arguments = intent.extras
        }

       if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, list)
                .commit()
        }

        if (savedInstanceState != null && resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, list)
                .commit()
        }

        if (savedInstanceState != null && resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, list)
                .commit()
        }
    }



}

package com.temporarygs.booksorting.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.temporarygs.booksorting.R
import com.temporarygs.booksorting.adapter.RecyclerHomeAdapter

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var Recycle = findViewById(R.id.list_Home) as RecyclerView
        val list = ArrayList<String>()
        list.add("Search")
        list.add("100~900 sorting")
        list.add("400 sorting")
        list.add("Clear")
        val adapter = RecyclerHomeAdapter(list)
        Recycle.adapter = adapter


    }
}
package com.temporarygs.booksorting.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.temporarygs.booksorting.R
import com.temporarygs.booksorting.model.data.Info
import java.time.format.DateTimeFormatter

class RecyclerSearchAdapter(private val items:ArrayList<Info>) : RecyclerView.Adapter<RecyclerSearchAdapter.ViewHolder>() {
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: RecyclerSearchAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.apply{
            bind(item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.search_item_list,parent,false)
        return ViewHolder(inflatedView)
    }
    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        private var title = v.findViewById(R.id.search_name) as TextView
        private var loc = v.findViewById(R.id.search_location) as TextView
        private var time = v.findViewById(R.id.search_time) as TextView
        fun bind(item: Info){
            title.setText(item.name)
            loc.setText(String.format("위치 : %d라인 %d번 %d열 %d줄",item.type,item.location,item.column,item.row))
            time.setText(item.find_time)
        }
    }
}
package com.temporarygs.booksorting.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.temporarygs.booksorting.R
import com.temporarygs.booksorting.activity.SearchActivity
import com.temporarygs.booksorting.activity.select.SelectActivity
import com.temporarygs.booksorting.activity.select.SelectSubActivity
import com.temporarygs.booksorting.model.db.InfoDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecyclerHomeAdapter(private val items:ArrayList<String>) : RecyclerView.Adapter<RecyclerHomeAdapter.ViewHolder>() {
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, item, Toast.LENGTH_SHORT).show()

            if(item.substring(0,3)=="400"){
                val intent = Intent(it.context, SelectSubActivity::class.java)
                intent.putExtra("type","sub")
                startActivity(it.context,intent,null)
            }else if(item=="Search") {
                val intent = Intent(it.context, SearchActivity::class.java)
                startActivity(it.context,intent,null)
            }else if(item.substring(0,3) =="100"){//
                val intent = Intent(it.context, SelectActivity::class.java)
                intent.putExtra("type","Main")
                startActivity(it.context,intent,null)
            }else if(item.substring(0,1)=="R"){

            }else if(item=="Clear"){
                CoroutineScope(Dispatchers.IO).launch {//IO쓰레드에서 처리 -> Main에서 db처리 불가
                    val db = InfoDb.getInstance(it.context)//db싱글톤 가져옴
                    db?.InfoDao()?.clear()//초기화
                }
            }

        }
        holder.apply{
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.home_item_list,parent,false)
        return ViewHolder(inflatedView)
    }
    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        private var view: View = v
        private var text = v.findViewById(R.id.home_btn) as TextView
        fun bind(listener: View.OnClickListener, item: String){
            text.setText(item)
            view.setOnClickListener(listener)
        }
    }

}
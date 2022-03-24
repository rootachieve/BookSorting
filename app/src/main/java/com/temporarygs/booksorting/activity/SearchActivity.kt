package com.temporarygs.booksorting.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.temporarygs.booksorting.R
import com.temporarygs.booksorting.adapter.RecyclerHomeAdapter
import com.temporarygs.booksorting.adapter.RecyclerSearchAdapter
import com.temporarygs.booksorting.model.data.Info
import com.temporarygs.booksorting.model.db.InfoDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {
    var ty = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        var Recycle = findViewById(R.id.list_search) as RecyclerView //검색 결과 보여주는 리사이클러

        var spinner =  findViewById(R.id.search_spinner) as Spinner //검색 태그를 정하는 spinner
        spinner.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ty = position//스피너의 값이 바뀌면 ty에 저장
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        var search = findViewById(R.id.search_search) as SearchView
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                var temp : List<Info>?//임시로 검색결과를 저장할 리스트
                temp = null
                if(ty==0){//일치
                    CoroutineScope(Dispatchers.IO).launch {//IO 쓰레드에서 db처리 -> main쓰레드에서 처리 불가
                        val db = InfoDb.getInstance(this@SearchActivity) //db싱글톤 얻어옴
                        if (query != null) {//쿼리가 빈값이 아니라면
                            temp = db?.InfoDao()?.findInfoByName(query)//일치하는 것을 검색
                            Log.e("Dbb",temp.toString())//로그
                            CoroutineScope(Dispatchers.Main).launch {//UI는 IO쓰레드에서 처리불가하기에 Main쓰레드
                                val temp2 = ArrayList<Info>()//Arraylist선언
                                temp?.let { temp2.addAll(it) }//LIst에서 Array list로 옮김
                                val adapter = RecyclerSearchAdapter(temp2)//어답터 생성
                                Recycle.adapter = adapter//어답터 지정
                            }
                        }
                    }
                }
                else if(ty==1){//위와 같고 쿼리로 시작하는 것 찾음
                    CoroutineScope(Dispatchers.IO).launch {
                        val db = InfoDb.getInstance(this@SearchActivity)
                        if (query != null) {
                            temp = db?.InfoDao()?.findInfoByStart(query)
                            Log.e("Dbb",temp.toString())
                            CoroutineScope(Dispatchers.Main).launch {
                                val temp2 = ArrayList<Info>()
                                temp?.let { temp2.addAll(it) }
                                val adapter = RecyclerSearchAdapter(temp2)
                                Recycle.adapter = adapter
                            }
                        }
                    }
                }
                else if(ty==2) {//쿼리로 끝나는 것 찾음
                    CoroutineScope(Dispatchers.IO).launch {

                        val db = InfoDb.getInstance(this@SearchActivity)
                        if (query != null) {
                            temp = db?.InfoDao()?.findInfoByEnd(query)
                            Log.e("Dbb",temp.toString())
                            CoroutineScope(Dispatchers.Main).launch {
                                val temp2 = ArrayList<Info>()
                                temp?.let { temp2.addAll(it) }
                                val adapter = RecyclerSearchAdapter(temp2)
                                Recycle.adapter = adapter
                            }
                        }
                    }
                }
                else if(ty==3) {//쿼리가 중간에 있는 것 찾음
                    CoroutineScope(Dispatchers.IO).launch {
                        val db = InfoDb.getInstance(this@SearchActivity)
                        if (query != null) {
                            temp = db?.InfoDao()?.findInfoByMiddle(query)
                            Log.e("Dbb",temp.toString())
                            CoroutineScope(Dispatchers.Main).launch {
                                val temp2 = ArrayList<Info>()
                                temp?.let { temp2.addAll(it) }
                                val adapter = RecyclerSearchAdapter(temp2)
                                Recycle.adapter = adapter
                            }
                        }
                    }

                }



                return true
            }
        })
    }
}
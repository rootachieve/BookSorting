package com.temporarygs.booksorting.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "SCanViewModel"
object ScanViewModel : ViewModel(){
    lateinit var string: String//청구기호
    var type = 0;//900,800 등등
    var col = 1 //번째 위치
    var row = 1 //번째 줄
    var loc = 1 //번째 열
    var sub = MutableLiveData<String>()//sub 관리

    fun increase(){//증가버튼 눌렀을 경우 -> 리스너는 xml에 있음
        if(type==900){
            row++
            if(row>6){
                row = 1
                col++;
            }
            if(col>4){
                col = 1
                loc++
            }

        }else if(type==650) {
            row++
            if(row>6){
                row = 1
                col++;
            }
            if(col>4){
                col = 1
                loc++
            }
        }else if(type==400){
            row++
            if(row>6){
                row = 1
                col++;
            }
            if(col>4){
                col = 1
                loc++
            }
        }else if(type==300){
            row++
            if(row>6){
                row = 1
                col++;
            }
            if(col>4){
                col = 1
                loc++
            }
        }else if(type==200){
            row++
            if(row>6){
                row = 1
                col++;
            }
            if(col>4){
                col = 1
                loc++
            }
        }else if(type==100){
            row++
            if(row>6){
                row = 1
                col++;
            }
            if(col>4){
                col = 1
                loc++
            }
        }else{
            row++
            if(row>6){
                row = 1
                col++;
            }
            if(col>4){
                col = 1
                loc++
            }
        }

        sub.value = String.format("%d번째 열 %d번째 책장 %d번째 줄",this.loc,this.col,this.row) // sub 갱신
    }

    fun decrease(){//감소 버튼 클릭 -> 리스너는 xml에 있음
        if(type==900){
            if(loc!=1||row!=1||col!=1) {
                row--
                if (row < 1) {
                    row = 6
                    col--
                }
                if (col < 1) {
                    col = 4
                    loc--
                }
            }

        }else if(type==650) {
            if(loc!=1||row!=1||col!=1) {
                row--
                if (row < 1) {
                    row = 6
                    col--
                }
                if (col < 1) {
                    col = 4
                    loc--
                }
            }
        }else if(type==400){
            if(loc!=1||row!=1||col!=1) {
                row--
                if (row < 1) {
                    row = 6
                    col--
                }
                if (col < 1) {
                    col = 4
                    loc--
                }
            }
        }else if(type==300){
            if(loc!=1||row!=1||col!=1) {
                row--
                if (row < 1) {
                    row = 6
                    col--
                }
                if (col < 1) {
                    col = 4
                    loc--
                }
            }
        }else if(type==200){
            if(loc!=1||row!=1||col!=1) {
                row--
                if (row < 1) {
                    row = 6
                    col--
                }
                if (col < 1) {
                    col = 4
                    loc--
                }
            }
        }else if(type==100){
            if(loc!=1||row!=1||col!=1) {
                row--
                if (row < 1) {
                    row = 6
                    col--
                }
                if (col < 1) {
                    col = 4
                    loc--
                }
            }
        }else{
            if(loc!=1||row!=1||col!=1) {
                row--
                if (row < 1) {
                    row = 6
                    col--
                }
                if (col < 1) {
                    col = 4
                    loc--
                }
            }
        }

        sub.value = String.format("%d번째 열 %d번째 책장 %d번째 줄",this.loc,this.col,this.row)//sub 갱신
    }
}
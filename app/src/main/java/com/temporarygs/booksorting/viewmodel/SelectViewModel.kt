package com.temporarygs.booksorting.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

object SelectViewModel : ViewModel() {
    var loc = 1
    var ty = 0
    var string = MutableLiveData<String>("000라인 1번째 책장")
    var move = MutableLiveData<Boolean>(false)

    fun selectClick(t : Int, l : Int){
        ty = t
        loc = l
        string.value = String.format("%03d라인 %d번째 책장",t,l)
    }

    fun buttonClick(){
        move.value = true
    }
}
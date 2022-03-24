package com.temporarygs.booksorting.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.temporarygs.booksorting.model.data.Info

@Database(entities = [
  Info::class
], version = 1)
abstract class InfoDb : RoomDatabase() {
  abstract fun InfoDao(): InfoDao

  companion object{
    private var INSTANCE:InfoDb? = null

      fun getInstance(context: Context): InfoDb?{
        if(INSTANCE == null){
          synchronized(InfoDb::class) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,InfoDb::class.java,"database").build()
          }
        }
        return INSTANCE
      }
  }
}
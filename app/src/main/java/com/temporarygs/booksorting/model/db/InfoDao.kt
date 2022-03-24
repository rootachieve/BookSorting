package com.temporarygs.booksorting.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.temporarygs.booksorting.model.data.Info

@Dao
interface InfoDao {
    @Query("SELECT * FROM Info WHERE name LIKE '%'||:Iname")
    fun findInfoByEnd(Iname : String): List<Info>?

    @Query("SELECT * FROM Info WHERE name LIKE :Iname||'%'")
    fun findInfoByStart(Iname : String): List<Info>?

    @Query("SELECT * FROM Info WHERE name LIKE '%'||:Iname||'%'")
    fun findInfoByMiddle(Iname : String): List<Info>?

    @Query("SELECT * FROM Info WHERE name = :Iname")
    fun findInfoByName(Iname : String): List<Info>?

    @Query("DELETE FROM Info")
    fun clear()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(info : Info)

}
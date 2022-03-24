package com.temporarygs.booksorting.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "Info")
data class Info(
    @PrimaryKey
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "location") var location : Int,
    @ColumnInfo(name = "type") var type : Int,
    @ColumnInfo(name = "row") var row : Int,
    @ColumnInfo(name = "column") var column : Int,
    @ColumnInfo(name = "find_time") var find_time : String
)
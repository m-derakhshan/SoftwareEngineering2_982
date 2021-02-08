package de.co.derakhshan.hamdad.database.tabels

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "BestAll", primaryKeys = ["id", "type"])
data class BestAll(

    @ColumnInfo(name = "id")
    val id: String = "",


    @ColumnInfo(name = "type")
    val type: String = "",

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "cover")
    val cover: String = "",


    @ColumnInfo(name = "score")
    val score: String = ""

)
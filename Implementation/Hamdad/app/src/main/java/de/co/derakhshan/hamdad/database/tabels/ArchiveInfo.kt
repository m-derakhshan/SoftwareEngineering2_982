package de.co.derakhshan.hamdad.database.tabels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ArchiveInfo")
data class ArchiveInfo(


    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",


    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "place")
    val place: String = "",

    @ColumnInfo(name = "cover")
    val cover: String = "",


    @ColumnInfo(name = "score")
    val score: String = "",

    @ColumnInfo(name = "date")
    val date: String = "",

    @ColumnInfo(name = "holder")
    val holder: String = ""


    )
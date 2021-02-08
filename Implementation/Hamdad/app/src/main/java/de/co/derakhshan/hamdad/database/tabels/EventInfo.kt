package de.co.derakhshan.hamdad.database.tabels


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "EventInfo")
data class EventInfo(

    @PrimaryKey
    val id: String = "",

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "cover")
    val cover: String = "",

    @ColumnInfo(name = "description")
    val description: String = "",

    @ColumnInfo(name = "event_date")
    val event_date: String ="" ,

    @ColumnInfo(name = "time")
    val time: String = "",

    @ColumnInfo(name = "place")
    val place: String = "",

    @ColumnInfo(name = "holder")
    val holder: String = "",

    @ColumnInfo(name = "isDone")
    val isDone: Boolean = false,

    @ColumnInfo(name = "isParticipant")
    val isParticipant: Boolean = false,

    @ColumnInfo(name = "score")
    val score: String = ""

)
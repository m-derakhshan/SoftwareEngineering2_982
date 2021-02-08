package de.co.derakhshan.hamdad.database.tabels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "ReminderInfo")
data class ReminderInfo(

    @PrimaryKey
    val id: String = "",

    @ColumnInfo(name = "description")
    val title: String = "",

    @ColumnInfo(name = "date")
    val date: String =""
)
package de.co.derakhshan.hamdad.database.tabels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserInfo")
data class UserInfo(
    @ColumnInfo
    val name: String = "",
    @PrimaryKey
    val identifyCode: String = "",
    @ColumnInfo
    val phone: String = "",
    @ColumnInfo
    val image: String = ""
)
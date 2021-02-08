package de.co.derakhshan.hamdad.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.co.derakhshan.hamdad.database.tabels.ReminderInfo

@Dao
interface ReminderDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(reminder: ReminderInfo)

    @Query("SELECT * from ReminderInfo ")
    fun getReminders(): LiveData<List<ReminderInfo>>

}
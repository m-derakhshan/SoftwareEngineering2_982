package de.co.derakhshan.hamdad.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.co.derakhshan.hamdad.database.tabels.BestAll


@Dao
interface BestAllDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(event: BestAll)

    @Query("SELECT * FROM BestAll WHERE type='event' ")
    fun getBestEvents(): LiveData<List<BestAll>>

    @Query("SELECT * FROM BestAll WHERE type='executor' ")
    fun getBestExecutor(): LiveData<List<BestAll>>

}
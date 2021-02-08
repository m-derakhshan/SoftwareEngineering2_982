package de.co.derakhshan.hamdad.database

import androidx.lifecycle.LiveData
import androidx.room.*
import de.co.derakhshan.hamdad.database.tabels.ArchiveInfo
@Dao
interface ArchiveDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: ArchiveInfo)


    @Query("SELECT * FROM ArchiveInfo ")
    fun getAllEvents(): LiveData<List<ArchiveInfo>>

}
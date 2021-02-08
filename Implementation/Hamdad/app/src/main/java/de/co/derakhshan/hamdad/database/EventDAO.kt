package de.co.derakhshan.hamdad.database

import androidx.lifecycle.LiveData
import androidx.room.*
import de.co.derakhshan.hamdad.database.tabels.EventInfo

@Dao
interface EventDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: EventInfo)

    @Update
    fun update(event: EventInfo)


    @Query("SELECT * FROM EventInfo WHERE  isDone= :isDone AND isParticipant = :isParticipant ")
    fun getReminderItem(
        isDone: Boolean = false,
        isParticipant: Boolean = true
    ): LiveData<List<EventInfo>>

    @Query("DELETE  FROM EventInfo ")
    fun deleteAll()


    @Query("SELECT * FROM EventInfo ")
    fun getAllEvent(): LiveData<List<EventInfo>>

    @Query("SELECT * FROM EventInfo where isDone=:isDone ")
    fun getDoneEvents(isDone: Boolean = true): LiveData<List<EventInfo>>


    @Query("SELECT * FROM EventInfo where isParticipant = (CASE  WHEN  :isRegistered is null THEN isParticipant ELSE :isRegistered  END)  AND isDone = (CASE  WHEN  :isDone is null THEN  isDone ELSE  (NOT :isDone)  END) AND (title LIKE '%' || :name ||'%' OR holder LIKE '%' || :name ||'%' )")
    fun filterEvents(
        isDone: Boolean?,
        isRegistered: Boolean?,
        name: String?
    ): LiveData<List<EventInfo>>


    @Query("SELECT * FROM EventInfo WHERE id=:id ")
    fun getEvent(id: String): LiveData<EventInfo>

    @Query("SELECT * FROM EventInfo WHERE  event_date BETWEEN :startDate AND :endDate")
    fun getMonthEvent(startDate: String, endDate: String): LiveData<List<EventInfo>>


}
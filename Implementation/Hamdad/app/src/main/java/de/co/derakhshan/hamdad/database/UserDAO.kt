package de.co.derakhshan.hamdad.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.co.derakhshan.hamdad.database.tabels.UserInfo

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserInfo)

    @Query("SELECT * FROM  UserInfo")
    fun getUser(): LiveData<UserInfo>
}

package de.co.derakhshan.hamdad.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.co.derakhshan.hamdad.database.tabels.*

@Database(
    entities = [EventInfo::class, UserInfo::class, ReminderInfo::class, BestAll::class, ArchiveInfo::class],
    version = 1,
    exportSchema = false
)
abstract class EventDatabase : RoomDatabase() {

    abstract val eventDao: EventDAO
    abstract val userDao: UserDAO
    abstract val reminderDao: ReminderDAO
    abstract val bestEvenDao: BestAllDAO
    abstract val archiveDao: ArchiveDAO

    companion object {

        @Volatile
        private var INSTANCE: EventDatabase? = null

        fun getInstance(context: Context): EventDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EventDatabase::class.java,
                        "event_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
package com.eneskayiklik.postit.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eneskayiklik.postit.db.DataConverter
import com.eneskayiklik.postit.db.dao.ReminderDao
import com.eneskayiklik.postit.db.entity.Reminder

@Database(
    entities = [Reminder::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun getDao(): ReminderDao

    companion object {
        const val DATABASE_NAME = "reminder.db"
    }
}
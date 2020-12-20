package com.eneskayiklik.postit.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eneskayiklik.postit.db.dao.BirthdayDao
import com.eneskayiklik.postit.db.entity.Birthday

@Database(
    entities = [Birthday::class],
    version = 1,
    exportSchema = false
)
abstract class BirthdayDatabase : RoomDatabase() {
    abstract fun getDao(): BirthdayDao

    companion object {
        const val DATABASE_NAME = "birthdays.db"
    }
}
package com.eneskayiklik.postit.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eneskayiklik.postit.db.DataConverter
import com.eneskayiklik.postit.db.dao.NoteDao
import com.eneskayiklik.postit.db.entity.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "notes.db"
    }
}
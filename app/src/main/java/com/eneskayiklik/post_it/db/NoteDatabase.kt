package com.eneskayiklik.post_it.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eneskayiklik.post_it.db.entity.Note

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getDao(): NoteDao

    companion object {
        const val DATABASE_NAME = "notes.db"
    }
}
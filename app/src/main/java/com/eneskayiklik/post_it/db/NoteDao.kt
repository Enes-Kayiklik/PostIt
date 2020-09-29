package com.eneskayiklik.post_it.db

import androidx.room.*
import com.eneskayiklik.post_it.db.entity.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<Note>

    @Delete
    suspend fun deleteNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)
}
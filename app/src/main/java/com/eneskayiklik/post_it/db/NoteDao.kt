package com.eneskayiklik.post_it.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<Note>

    @Delete
    suspend fun deleteNote(note: Note)

    @Insert
    suspend fun addNote(note: Note)
}
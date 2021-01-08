package com.eneskayiklik.postit.db.dao

import androidx.room.*
import com.eneskayiklik.postit.db.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM notes WHERE title LIKE '%' || :searchQuery || '%'")
    fun getAllNotes(searchQuery: String): Flow<List<Note>>

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes ORDER BY date")
    fun getAllNotesOrderByDate(): List<Note>
}
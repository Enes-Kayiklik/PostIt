package com.eneskayiklik.post_it.db

import androidx.room.*
import com.eneskayiklik.post_it.db.entity.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE title LIKE '%' || :searchQuery || '%'")
    fun getAllNotes(searchQuery: String): Flow<List<Note>>

    @Query("SELECT * FROM notes WHERE id==:id")
    fun getCurrentNote(id: Int): Flow<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes ORDER BY date")
    fun getAllNotesOrderByDate(): List<Note>
}
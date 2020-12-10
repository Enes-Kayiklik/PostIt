package com.eneskayiklik.post_it.ui.main.notes

import com.eneskayiklik.post_it.db.NoteDao
import com.eneskayiklik.post_it.db.entity.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val dao: NoteDao
) {
    fun getAllNotes(searchQuery: String): Flow<List<Note>> = dao.getAllNotes(searchQuery)

    suspend fun addNote(note: Note) = dao.addNote(note)

    suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    suspend fun deleteAllNote() = dao.deleteAllNotes()
}
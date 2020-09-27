package com.eneskayiklik.post_it.ui.main

import com.eneskayiklik.post_it.db.Note
import com.eneskayiklik.post_it.db.NoteDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val dao:NoteDao
) {
    fun getAllNotes() = dao.getAllNotes()
    suspend fun addNote(note: Note) = dao.addNote(note)
    suspend fun deleteNote(note: Note) = dao.deleteNote(note)
}
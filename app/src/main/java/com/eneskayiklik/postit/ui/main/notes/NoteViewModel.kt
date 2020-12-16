package com.eneskayiklik.postit.ui.main.notes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.postit.db.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class NoteViewModel @ViewModelInject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {
    var searchQuery = MutableStateFlow("")
    private val searchFlows = searchQuery.flatMapLatest {
        noteRepository.getAllNotes(it)
    }
    var notes = searchFlows.asLiveData()

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.addNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteAllNote()
        }
    }
}
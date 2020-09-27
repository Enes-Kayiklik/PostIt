package com.eneskayiklik.post_it.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskayiklik.post_it.db.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel @ViewModelInject constructor(
    private val noteRepository: MainRepository
): ViewModel() {
    private var _allNotes = MutableLiveData<List<Note>>()
    val allNotes: LiveData<List<Note>>
        get() = _allNotes

    fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _allNotes.postValue(noteRepository.getAllNotes())
        }
    }

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
}
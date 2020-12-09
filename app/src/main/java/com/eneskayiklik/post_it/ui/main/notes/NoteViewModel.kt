package com.eneskayiklik.post_it.ui.main.notes

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.eneskayiklik.post_it.db.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class NoteViewModel @ViewModelInject constructor(
    private val noteRepository: NoteRepository
): ViewModel() {
    var searchQuery = MutableStateFlow("")
    private val flows = searchQuery.flatMapLatest {
        noteRepository.getAllNotes(it)
    }

    var notes = flows.asLiveData()
    private var _allNotes = MutableLiveData<List<Note>>()
    val allNotes: LiveData<List<Note>>
        get() = _allNotes

    /*fun getAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            _allNotes.postValue(noteRepository.getAllNotes().asLiveData())
        }
    }*/

    fun getAllNotesOrderByDate() {
        viewModelScope.launch(Dispatchers.IO) {
            _allNotes.postValue(noteRepository.getAllNotesOrderByDate())
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.addNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNote(note)
            Log.e("TAG", "updateNote:")
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            //noteRepository.deleteNote(note).also { getAllNotes() }
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            //noteRepository.deleteAllNote().also { getAllNotes() }
        }
    }
}
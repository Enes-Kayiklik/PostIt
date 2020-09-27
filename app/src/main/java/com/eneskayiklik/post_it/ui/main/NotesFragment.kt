package com.eneskayiklik.post_it.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.Note
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel.addNote(Note(
            title = "Başlık",
            description = "Desc",
            date = 46465L
        ))
    }
}
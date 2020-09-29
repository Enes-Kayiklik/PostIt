package com.eneskayiklik.post_it.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notes.*

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonsOnClick()
        setupObserver()
        noteViewModel.getAllNotes()
    }

    private fun setupObserver() {
        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer {
            setupRecyclerView(it)
        })
    }

    private fun setupRecyclerView(noteList: List<Note>) {
        recyclerViewNotes.apply {
            adapter = NoteAdapter(noteList, noteViewModel)
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        }
    }

    private fun setupButtonsOnClick() {
        btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment)
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sortByDate -> {
                    noteViewModel.getAllNotesOrderByDate()
                }

                R.id.deleteAllNotes -> {
                    noteViewModel.deleteAllNotes()
                }
            }
            true
        }
    }
}
package com.eneskayiklik.post_it.ui.main.notes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
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
    private lateinit var staggeredGrid: StaggeredGridLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtonsOnClick()
        setupObserver()
        staggeredGrid = StaggeredGridLayoutManager(
            1,
            StaggeredGridLayoutManager.VERTICAL
        )
        setHasOptionsMenu(true)
    }

    private fun setupObserver() {
        noteViewModel.notes.observe(viewLifecycleOwner, Observer {
            setupRecyclerView(it)
        })
    }
    private fun setupRecyclerView(noteList: List<Note>) {
        recyclerViewNotes.apply {
            adapter = NoteAdapter(
                noteList,
                noteViewModel
            ) {
                if (it == 0)
                    staggeredGrid.spanCount = 1
            }
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
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_fragment_menu, menu)
        val searchView = menu.findItem(R.id.searchNote).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true

            override fun onQueryTextChange(newText: String?): Boolean {
                noteViewModel.searchQuery.value = newText.orEmpty()
                return true
            }
        })
    }
}
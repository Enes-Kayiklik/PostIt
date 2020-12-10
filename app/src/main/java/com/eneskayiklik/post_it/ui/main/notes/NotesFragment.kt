package com.eneskayiklik.post_it.ui.main.notes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Note
import com.eneskayiklik.post_it.util.makeInvisible
import com.eneskayiklik.post_it.util.makeVisible
import com.eneskayiklik.post_it.util.onQueryTextChanged
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_notes.*

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var staggeredGrid: StaggeredGridLayoutManager
    private lateinit var noteAdapter: NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
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
            if (it.isEmpty()) emptyNoteLayout.makeVisible() else emptyNoteLayout.makeInvisible()
            noteAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter {
            noteViewModel.deleteNote(it)
            showSnackbar(it)
        }

        recyclerViewNotes.apply {
            adapter = noteAdapter
            setHasFixedSize(true)
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        }
    }

    private fun showSnackbar(note: Note) {
        val snackbar = Snackbar.make(this.requireView(), "Deleted successful", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                noteViewModel.addNote(note)
            }
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val layoutParams = snackbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.setMargins(32, 0, 32, 32)
        snackbarLayout.layoutParams = layoutParams
        snackbar.show()
    }

    private fun setupButtonsOnClick() {
        btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addNoteFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_fragment_menu, menu)
        val searchView = menu.findItem(R.id.searchNote).actionView as SearchView
        searchView.onQueryTextChanged {
            noteViewModel.searchQuery.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.deleteAllNotes -> {
            noteViewModel.deleteAllNotes()
            true
        }
        else -> false
    }
}
package com.eneskayiklik.post_it.ui.main.notes

import android.app.AlertDialog
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
class NotesFragment : Fragment(R.layout.fragment_notes), NoteAdapter.OnItemClickListener {
    private val noteViewModel: NoteViewModel by viewModels()
    private lateinit var noteAdapter: NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupButtonsOnClick()
        setupObserver()
        setHasOptionsMenu(true)
    }

    private fun setupObserver() {
        noteViewModel.notes.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) emptyNote.makeVisible() else emptyNote.makeInvisible()
            noteAdapter.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter(this)
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
        val snackbar =
            Snackbar.make(this.requireView(), R.string.deleted_successful, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo) {
                    noteViewModel.addNote(note)
                }
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        val layoutParams = snackbarLayout.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.setMargins(32, 0, 32, 32)
        snackbarLayout.layoutParams = layoutParams
        snackbar.show()
    }

    private fun deleteAllNotes(): Boolean {
        AlertDialog.Builder(this.requireContext())
            .setTitle(R.string.delete)
            .setMessage(R.string.delete_all_notes_text)
            .setPositiveButton(R.string.yes) { _, _ ->
                noteViewModel.deleteAllNotes()
            }
            .setNegativeButton(R.string.no) { _, _ -> }.show()
        return true
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
        R.id.deleteAllNotes -> deleteAllNotes()
        else -> false
    }

    override fun onItemLongClicked(note: Note) {
        AlertDialog.Builder(this.requireContext())
            .setTitle(R.string.delete)
            .setMessage(resources.getString(R.string.delete_note_text).plus(" '${note.title} ?"))
            .setPositiveButton(R.string.yes) { _, _ ->
                noteViewModel.deleteNote(note)
                showSnackbar(note)
            }
            .setNegativeButton(R.string.no) { _, _ -> }.show()
    }

    override fun onItemClicked(note: Note) {
        findNavController().navigate(
            NotesFragmentDirections.actionNotesFragmentToAddNoteFragment(note)
        )
    }
}
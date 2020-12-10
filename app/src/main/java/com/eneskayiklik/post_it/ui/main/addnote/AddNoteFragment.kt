package com.eneskayiklik.post_it.ui.main.addnote

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Note
import com.eneskayiklik.post_it.db.entity.Todo
import com.eneskayiklik.post_it.ui.main.notes.NoteViewModel
import com.eneskayiklik.post_it.util.convertHumanTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_note.*

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {
    private val noteViewModel: NoteViewModel by viewModels()
    private val navArgs by navArgs<AddNoteFragmentArgs>()
    private var todoListAdapter = TodoListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setupButtonsOnClick()
        setHasOptionsMenu(true)
    }

    private fun setData() {
        tvDate.text = System.currentTimeMillis().convertHumanTime()
        navArgs.currentNote?.let { currentNote ->
            tvDate.text = currentNote.date.convertHumanTime()
            edtNoteTitle.setText(currentNote.title)
            edtNote.setText(currentNote.description)
            todoListAdapter.submitList(currentNote.todoList)
            tvTitleLength.text = "${currentNote.title.length}".plus(" / 15")
        }
        recyclerViewTodoList.adapter = todoListAdapter
    }

    private fun setupButtonsOnClick() {
        edtNoteTitle.doOnTextChanged { text, _, _, _ ->
            tvTitleLength.text = "${text?.length ?: 0}".plus(" / 15")
        }
    }

    private fun saveNote(): Boolean {
        val note = edtNote.text.toString()
        val title = edtNoteTitle.text.toString()

        return if (note.isNotEmpty() && title.isNotEmpty()) {
            noteViewModel.addNote(
                Note(
                    id = navArgs.currentNote?.id ?: 0,
                    title = title,
                    description = note,
                    date = System.currentTimeMillis(),
                    todoList = todoListAdapter.currentList.filter { it.title.isNotEmpty() }
                )
            )
            this.requireActivity().onBackPressed()
            true
        } else {
            Toast.makeText(this.requireContext(), "Input all require data", Toast.LENGTH_SHORT)
                .show()
            false
        }
    }

    private fun addTodoListItem(): Boolean {
        val list = todoListAdapter.currentList.toMutableSet()
        list.add(Todo())
        todoListAdapter.submitList(list.toList())
        return true
    }

    private fun deleteCurrentNote(note: Note?): Boolean {
        note?.let {
            noteViewModel.deleteNote(it)
        }
        this.requireActivity().onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.saveNote -> saveNote()
        R.id.addNote -> addTodoListItem()
        R.id.deleteNote -> deleteCurrentNote(navArgs.currentNote)
        else -> super.onOptionsItemSelected(item)
    }
}
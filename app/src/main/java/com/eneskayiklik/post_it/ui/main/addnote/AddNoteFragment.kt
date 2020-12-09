package com.eneskayiklik.post_it.ui.main.addnote

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Note
import com.eneskayiklik.post_it.ui.main.notes.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {
    private val noteViewModel: NoteViewModel by viewModels()
    private val navArgs by navArgs<AddNoteFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setTime()
        setupButtonsOnClick()
        setHasOptionsMenu(true)
    }

    private fun setData() {
        navArgs.currentNote?.let { currentNote ->
            tvDate.text = SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.ROOT).format(currentNote.date)
            edtNoteTitle.setText(currentNote.title)
            edtNote.setText(currentNote.description)
        }
    }

    private fun setupButtonsOnClick() {
        edtNoteTitle.doOnTextChanged { text, _, _, _ ->
            tvTitleLength.text = "${text?.length ?: 0}".plus(" / 15")
        }
    }

    private fun addNoteToDb() {
        val note = edtNote.text.toString()
        val time = System.currentTimeMillis()
        val title = edtNoteTitle.text.toString()

        if (note.isNotEmpty() && title.isNotEmpty()) {
            noteViewModel.addNote(
                Note(
                    id = navArgs.currentNote?.id ?: 0,
                    title = title,
                    description = note,
                    date = time
                )
            )

            this.requireActivity().onBackPressed()
        }
    }

    private fun setTime() {
        tvDate.text =
            SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.ROOT).format(System.currentTimeMillis())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.saveNote -> {
            addNoteToDb()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
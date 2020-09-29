package com.eneskayiklik.post_it.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_note.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddNoteFragment : Fragment(R.layout.fragment_add_note) {
    private val noteViewModel: NoteViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTime()
        setupButtonsOnClick()
    }

    private fun setupButtonsOnClick() {
        btnAdd.setOnClickListener {
            addNoteToDb()
        }

        btnBack.setOnClickListener {
            this.requireActivity().onBackPressed()
        }

        btnCancel.setOnClickListener {
            this.requireActivity().onBackPressed()
        }

        edtNoteTitle.doOnTextChanged { text, _, _, _ ->
            tvTitleLength.text = "${text?.length}".plus(" / 20")
        }

        edtNote.doOnTextChanged { text, _, _, _ ->
            tvNoteLength.text = "${text?.length}".plus(" / 1000")
        }
    }

    private fun addNoteToDb() {
        val note = edtNote.text.toString()
        val time = System.currentTimeMillis()
        val title = edtNoteTitle.text.toString()

        if (note.isNotEmpty() && title.isNotEmpty()) {
            noteViewModel.addNote(
                Note(
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
}
package com.eneskayiklik.post_it.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Note
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_note_detail.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {
    private val noteViewModel: NoteViewModel by viewModels()
    private val args by navArgs<NoteDetailFragmentArgs>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setupButtonsOnClick()
    }

    private fun setupButtonsOnClick() {
        btnAddDetail.setOnClickListener {
            addNoteToDb()
        }

        btnBackDetail.setOnClickListener {
            this.requireActivity().onBackPressed()
        }

        btnCancelDetail.setOnClickListener {
            this.requireActivity().onBackPressed()
        }

        edtNoteTitleDetail.doOnTextChanged { text, _, _, _ ->
            tvTitleLengthDetail.text = "${text?.length}".plus(" / 20")
        }

        edtNoteDetail.doOnTextChanged { text, _, _, _ ->
            tvNoteLengthDetail.text = "${text?.length}".plus(" / 1000")
        }
    }

    private fun addNoteToDb() {
        val note = edtNoteDetail.text.toString()
        val time = System.currentTimeMillis()
        val title = edtNoteTitleDetail.text.toString()

        if (note.isNotEmpty() && title.isNotEmpty()) {
            noteViewModel.addNote(
                Note(
                    id = args.currentNote.id,
                    title = title,
                    description = note,
                    date = time
                )
            )
            this.requireActivity().onBackPressed()
        }
    }

    private fun setData() {
        val note = args.currentNote
        tvDateDetail.text = SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.ROOT).format(note.date)
        edtNoteTitleDetail.setText(note.title)
        edtNoteDetail.setText(note.description)
    }
}
package com.eneskayiklik.post_it.ui.main

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Note
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.one_row_note.view.*

class NoteAdapter(
    private val noteList: List<Note>,
    private val noteViewModel: NoteViewModel
) : RecyclerView.Adapter<NoteAdapter.CustomViewHolder>() {
    override fun getItemCount() = noteList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.one_row_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemView.apply {
            tvDesc.text = noteList[position].description
            tvTitle.text = noteList[position].title

            setOnClickListener {
                findNavController().navigate(
                    NotesFragmentDirections.actionNotesFragmentToNoteDetailFragment(noteList[position])
                )
            }

            setOnLongClickListener {
                AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Delete")
                    .setMessage("Do you want to delete '${noteList[position].title}'")
                    .setPositiveButton("Yes") { _, _ ->
                        noteViewModel.deleteNote(noteList[position])
                        showSnackbar(it, noteList[position], position)
                    }
                    .setNegativeButton("No") { _, _ ->

                    }.show()
                true
            }
        }
    }

    private fun showSnackbar(view: View, note: Note, position: Int) {
        Snackbar.make(view, "Deleted succesful", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                noteViewModel.addNote(note)
            }.show()
    }


    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
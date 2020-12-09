package com.eneskayiklik.post_it.ui.main.notes

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Note
import kotlinx.android.synthetic.main.one_row_note.view.*

class NoteAdapter(
    private val onNoteDeleted: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.CustomViewHolder>(CustomCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.one_row_note, parent, false)
        ) {
            onNoteDeleted(it)
        }
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CustomViewHolder(
        itemView: View,
        private val onNoteDeleted: (Note) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) {
            itemView.apply {
                tvDesc.text = note.description
                tvTitle.text = note.title

                setOnClickListener {
                    findNavController().navigate(
                        NotesFragmentDirections.actionNotesFragmentToAddNoteFragment(note)
                    )
                }

                setOnLongClickListener {
                    AlertDialog.Builder(itemView.context)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete '${note.title}'")
                        .setPositiveButton("Yes") { _, _ ->
                            onNoteDeleted(note)
                        }
                        .setNegativeButton("No") { _, _ ->

                        }.show()
                    true
                }
            }
        }
    }

    class CustomCallBack : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }
}
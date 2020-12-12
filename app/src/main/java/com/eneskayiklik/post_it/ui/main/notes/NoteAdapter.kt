package com.eneskayiklik.post_it.ui.main.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Note
import kotlinx.android.synthetic.main.one_row_note.view.*

class NoteAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Note, NoteAdapter.CustomViewHolder>(CustomCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.one_row_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CustomViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.apply {
                rootView.setOnLongClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = getItem(position)
                        onItemClickListener.onItemLongClicked(note)
                    }
                    true
                }

                rootView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = getItem(position)
                        onItemClickListener.onItemClicked(note)
                    }
                }
            }
        }

        fun bind(note: Note) {
            itemView.apply {
                tvDesc.text = note.description
                tvTitle.text = note.title
            }
        }
    }

    class CustomCallBack : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem == newItem
    }

    interface OnItemClickListener {
        fun onItemLongClicked(note: Note)
        fun onItemClicked(note: Note)
    }
}
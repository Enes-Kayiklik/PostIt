package com.eneskayiklik.postit.ui.main.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.db.entity.Note
import com.eneskayiklik.postit.util.extensions.advancedSubList
import com.eneskayiklik.postit.util.extensions.hide
import com.eneskayiklik.postit.util.extensions.show
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
                view.setOnLongClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val note = getItem(position)
                        onItemClickListener.onItemLongClicked(note)
                    }
                    true
                }

                view.setOnClickListener {
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
                if (note.todoList.isNotEmpty()) {
                    tvDesc.hide()
                    recyclerViewSmallTodoList.apply {
                        show()
                        adapter = SmallCheckAdapter(note.todoList.advancedSubList(0, 4))
                    }
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

    interface OnItemClickListener {
        fun onItemLongClicked(note: Note)
        fun onItemClicked(note: Note)
    }
}
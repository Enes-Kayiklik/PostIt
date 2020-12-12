package com.eneskayiklik.post_it.ui.main.addnote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.post_it.R
import com.eneskayiklik.post_it.db.entity.Todo
import kotlinx.android.synthetic.main.one_row_todo_list_item.view.*

class TodoListAdapter(
    private val listener: OnItemCheckedChange
) : ListAdapter<Todo, TodoListAdapter.CustomViewHolder>(CustomCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.one_row_todo_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CustomViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.checkBoxTodoIsDone.setOnCheckedChangeListener { _, b ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val todo = getItem(position)
                    listener.onItemCheckedChange(
                        todo,
                        b,
                        position
                    )
                }
            }
        }

        fun bind(todo: Todo) {
            itemView.apply {
                checkBoxTodoIsDone.isChecked = todo.isDone
                edtTodoTitle.setText(todo.title)
                edtTodoTitle.paint.isStrikeThruText = todo.isDone

                edtTodoTitle.doOnTextChanged { text, _, _, _ ->
                    todo.title = text?.toString() ?: ""
                }
            }
        }
    }

    class CustomCallBack : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo) =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo) =
            oldItem == newItem
    }

    interface OnItemCheckedChange {
        fun onItemCheckedChange(todo: Todo, isChecked: Boolean, position: Int)
    }
}
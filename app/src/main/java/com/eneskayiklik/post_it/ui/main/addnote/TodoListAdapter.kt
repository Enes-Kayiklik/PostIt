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

class TodoListAdapter : ListAdapter<Todo, TodoListAdapter.CustomViewHolder>(CustomCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.one_row_todo_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CustomViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(todo: Todo) {
            itemView.apply {
                checkBoxTodoIsDone.isChecked = todo.isDone
                edtTodoTitle.setText(todo.title)
                edtTodoTitle.paint.isStrikeThruText = todo.isDone
                checkBoxTodoIsDone.setOnCheckedChangeListener { _, b ->
                    todo.isDone = b
                    edtTodoTitle.paint.isStrikeThruText = b
                }

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
}
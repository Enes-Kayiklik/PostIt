package com.eneskayiklik.postit.ui.main.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.db.entity.Todo
import kotlinx.android.synthetic.main.one_row_small_todo_list_item.view.*

class SmallCheckAdapter(
    private val todoList: List<Todo>
) : RecyclerView.Adapter<SmallCheckAdapter.CustomViewHolder>() {
    override fun getItemCount(): Int = todoList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.one_row_small_todo_list_item, parent, false)
        )


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todo: Todo) {
            itemView.apply {
                tvTodoSmall.text = todo.title
                tvTodoSmall.paint.isStrikeThruText = todo.isDone
                checkBoxTodoSmall.isChecked = todo.isDone
            }
        }
    }
}
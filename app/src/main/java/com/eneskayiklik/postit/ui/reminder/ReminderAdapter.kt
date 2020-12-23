package com.eneskayiklik.postit.ui.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.db.entity.Reminder
import com.eneskayiklik.postit.util.makeInvisible
import kotlinx.android.synthetic.main.one_row_reminder.view.*

class ReminderAdapter :
    ListAdapter<Reminder, ReminderAdapter.CustomViewHolder>(CustomCallBack()) {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(reminder: Reminder, position: Int) {
            itemView.apply {
                textView.text = reminder.title
                if (position == 0)
                    separator.makeInvisible()
            }
        }
    }

    class CustomCallBack : DiffUtil.ItemCallback<Reminder>() {
        override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.one_row_reminder, parent, false)
        )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}
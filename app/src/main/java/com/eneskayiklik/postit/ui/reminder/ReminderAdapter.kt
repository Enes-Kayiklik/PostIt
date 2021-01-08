package com.eneskayiklik.postit.ui.reminder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.db.entity.Reminder
import com.eneskayiklik.postit.util.makeVisible
import kotlinx.android.synthetic.main.one_row_reminder.view.*

class ReminderAdapter(
    val onClickFun: (Reminder) -> Unit
) :
    ListAdapter<Reminder, ReminderAdapter.CustomViewHolder>(CustomCallBack()) {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.rootView.setOnClickListener {
                onClickFun(currentList[adapterPosition])
            }
        }

        fun bind(reminder: Reminder) {
            itemView.apply {
                tvReminderTitle.text = reminder.title
                tvReminderDetail.text = reminder.desc
                rootView.setBackgroundColor(reminder.reminderColor)
                if (reminder.isReminderActive)
                    icIsReminderActive.makeVisible()
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
        holder.bind(getItem(position))
    }
}
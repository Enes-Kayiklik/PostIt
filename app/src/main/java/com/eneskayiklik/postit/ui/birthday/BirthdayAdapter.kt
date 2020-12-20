package com.eneskayiklik.postit.ui.birthday

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eneskayiklik.postit.R
import com.eneskayiklik.postit.db.entity.Birthday
import kotlinx.android.synthetic.main.one_row_birthday.view.*

class BirthdayAdapter :
    ListAdapter<Birthday, BirthdayAdapter.CustomViewHolder>(BirthdayAdapter.CustomCallBack()) {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(birthday: Birthday) {
            itemView.apply {
                textView.text = birthday.name
            }
        }
    }

    class CustomCallBack : DiffUtil.ItemCallback<Birthday>() {
        override fun areItemsTheSame(oldItem: Birthday, newItem: Birthday) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Birthday, newItem: Birthday) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder =
        CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.one_row_birthday, parent, false)
        )

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
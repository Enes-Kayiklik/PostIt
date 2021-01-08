package com.eneskayiklik.postit.db.entity

import android.graphics.Color
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "reminder")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val desc: String = "",
    val reminderColor: Int = Color.WHITE,
    val isReminderActive: Boolean = false
) : Parcelable
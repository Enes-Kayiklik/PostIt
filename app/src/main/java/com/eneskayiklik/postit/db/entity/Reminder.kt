package com.eneskayiklik.postit.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "birthday")
data class Reminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val desc: String = ""
)
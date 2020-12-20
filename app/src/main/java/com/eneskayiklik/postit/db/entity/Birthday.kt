package com.eneskayiklik.postit.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eneskayiklik.postit.util.calculateAge

@Entity(tableName = "birthday")
data class Birthday(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: Long = 0L,
    val name: String = ""
) {
    val age: Int
        get() = this.date.calculateAge()
}
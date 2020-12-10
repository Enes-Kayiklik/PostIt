package com.eneskayiklik.post_it.db

import androidx.room.TypeConverter
import com.eneskayiklik.post_it.db.entity.Todo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class DataConverter : Serializable {
    companion object {
        private val todoType = object : TypeToken<List<Todo>>() {}.type
    }

    @TypeConverter
    fun fromTodoList(value: List<Todo>): String =
        if (value.isNotEmpty()) {
            Gson().toJson(value, todoType)
        } else ""

    @TypeConverter
    fun toTodoList(value: String): List<Todo> =
        if (value.isNotEmpty()) {
            Gson().fromJson(value, todoType)
        } else emptyList()
}
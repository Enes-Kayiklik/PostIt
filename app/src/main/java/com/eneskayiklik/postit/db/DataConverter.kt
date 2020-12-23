package com.eneskayiklik.postit.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.eneskayiklik.postit.db.entity.Todo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
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

    @TypeConverter
    fun fromBitmap(img: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        img.compress(
            Bitmap.CompressFormat.PNG, 100, outputStream
        )
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap =
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}
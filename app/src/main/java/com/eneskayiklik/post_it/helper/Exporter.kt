package com.eneskayiklik.post_it.helper

import android.content.Context
import android.net.Uri
import android.util.Log
import com.eneskayiklik.post_it.db.entity.Note
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class Exporter(
    private val context: Context
) {
    companion object {
        private val todoExportType = object : TypeToken<List<Note>>() {}.type
    }

    fun exportData(uri: Uri, note: List<Note>, fileName: String) {
        File(getExportedPath(), fileName).apply {
            appendText(note.toJson())
            writeFile(uri, this)
        }
    }

    fun importData(uri: Uri): List<Note>? = context.contentResolver.openInputStream(uri).run {
        this?.bufferedReader()?.readText()?.fromJson()
    }

    private fun List<Note>.toJson() =
        Gson().toJson(this, todoExportType)


    private fun String.fromJson(): List<Note> {
        val str = if (this.startsWith("[]"))
            this.replaceFirst("[]", "[{")
        else this
        return try {
            Gson().fromJson(str, todoExportType)
        } catch (e: Exception) {
            Log.e("From Json Exception", "$e")
            emptyList()
        }
    }

    private fun writeFile(destinationUri: Uri, file: File) {
        val outputStream = context.contentResolver.openOutputStream(destinationUri)
        outputStream?.apply {
            write(file.readBytes())
            close()
        }
    }

    private fun getExportedPath(): File {
        val filePath = File(context.cacheDir, "exported data")
        if (!filePath.exists()) {
            filePath.mkdir()
        }
        filePath.listFiles()?.forEach { it.delete() }
        return filePath
    }
}
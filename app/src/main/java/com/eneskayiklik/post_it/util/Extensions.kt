package com.eneskayiklik.post_it.util

import android.view.View
import androidx.appcompat.widget.SearchView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Returns given Long time millis to string as dd.MM.yyyy HH.mm
 */
fun Long.convertHumanTime(): String =
    SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.ROOT).format(this)

inline fun SearchView.onQueryTextChanged(crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = true

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText ?: "")
            return true
        }
    })
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.GONE
}
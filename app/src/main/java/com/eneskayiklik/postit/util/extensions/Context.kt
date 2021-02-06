package com.eneskayiklik.postit.util.extensions

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import java.util.*

fun Context.showToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()
}

fun Context.changeLanguage(language: String) {
    val locale = Locale(language)
    val config = Configuration().apply {
        setLocale(locale)
    }
    this.resources.configuration.setLocale(locale)
    //this.resources.updateConfiguration(config, this.resources?.displayMetrics)
}
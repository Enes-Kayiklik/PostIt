package com.eneskayiklik.postit.util

import android.content.Context
import android.content.res.Configuration
import java.util.*

class ContextUtils {

    companion object {
        fun changeLanguage(language: String, context: Context) {
            val locale = Locale(language)
            val config = Configuration().apply {
                setLocale(locale)
            }
            context.resources.updateConfiguration(config, context.resources?.displayMetrics)
        }
    }
}
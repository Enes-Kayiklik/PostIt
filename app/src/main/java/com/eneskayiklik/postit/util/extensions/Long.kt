package com.eneskayiklik.postit.util.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Returns given Long time millis to string as dd.MM.yyyy HH.mm
 */
fun Long.convertHumanTime(): String =
    SimpleDateFormat("dd.MM.yyyy HH.mm", Locale.ROOT).format(this)
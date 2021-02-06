package com.eneskayiklik.postit.util.extensions

import android.view.View

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

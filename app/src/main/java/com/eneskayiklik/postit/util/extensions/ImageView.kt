package com.eneskayiklik.postit.util.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadWithGlide(
    imageUrl: String,
    @DrawableRes placeholderRes: Int? = null,
    @DrawableRes errorRes: Int? = null
) {
    val requestOptions = RequestOptions().apply {
        placeholderRes?.let { placeholder(it) }
        errorRes?.let { error(it) }
    }

    Glide.with(this)
        .load(imageUrl)
        .apply(requestOptions)
        .into(this)
}
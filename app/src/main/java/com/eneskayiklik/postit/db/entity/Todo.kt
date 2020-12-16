package com.eneskayiklik.postit.db.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo(
    var isDone: Boolean = false,
    var title: String = ""
) : Parcelable
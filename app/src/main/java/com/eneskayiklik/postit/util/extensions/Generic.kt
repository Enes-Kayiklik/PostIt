package com.eneskayiklik.postit.util.extensions

import com.eneskayiklik.postit.util.LayoutType

fun <T> List<T>.advancedSubList(fromIndex: Int, toIndex: Int) =
    if (this.size > toIndex) this.subList(fromIndex, toIndex) else this

fun Long.calculateAge(): Int = 0

fun LayoutType.changeLayoutType() =
    if (this == LayoutType.NOTE) LayoutType.LIST else LayoutType.NOTE
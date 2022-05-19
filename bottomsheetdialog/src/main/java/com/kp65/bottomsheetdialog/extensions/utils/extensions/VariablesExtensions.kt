package com.kp65.bottomsheetdialog.extensions.utils.extensions


fun String?.isNonEmpty(): Boolean {
    return this?.let { it ->
        return trim { it <= ' ' }.isNotEmpty() && it != ""
    } ?: run { false }
}

fun String?.default(defaultValue: String? = ""): String {
    return this?.let { it ->
        if (isNonEmpty()) it else defaultValue ?: ""
    } ?: run { defaultValue ?: "" }
}

fun <T> MutableList<T>?.isNonEmpty(): Boolean {
    return this?.let {
        isNotEmpty() && size > 0
    } ?: run {
        false
    }
}

fun <T> MutableList<T>?.isValidPosition(position: Int): Boolean {
    return this?.let {
        isNotEmpty() && size > 0 && position >= 0 && position < size
    } ?: run {
        false
    }
}
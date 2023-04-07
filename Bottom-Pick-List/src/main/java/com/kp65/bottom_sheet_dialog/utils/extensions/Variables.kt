package com.kp65.bottom_sheet_dialog.utils.extensions

import android.content.Context
import android.util.TypedValue
import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException


fun String?.isNonEmpty(): Boolean {
    return this?.let { it -> return trim { it <= ' ' }.isNotEmpty() && it != "" } ?: false
}

fun String?.isEmpty(): Boolean {
    return this?.let { !it.isNonEmpty() }?:true
}

fun String?.default(defaultValue: String? = ""): String {
    return this?.takeIf { isNotEmpty() }?.let { this } ?: run { defaultValue ?: "" }
}

fun String?.underLine():String{
    return this?.takeIf { it.isNonEmpty() }?.let { "<u>$it</u>" } ?: ""
}

fun String?.getStare(color:String):String{
    return this?.takeIf { it.isNonEmpty() }?.replace("*", "<font color=$color>*</font>").default()
}

fun String?.removeLastChar(sign: Char): String {
    return this?.takeIf { it.isNonEmpty() }?.let {text ->
        var str = text.trim()
        if (!str.equals("", ignoreCase = true) && str.isNotEmpty() && str[str.length - 1] == sign) {
            str = str.substring(0, str.length - 1)
        }
        str
    }?:""
}

fun String?.isValidUrl(): Boolean {
    return this?.takeIf { it.isNonEmpty() }?.let {
        try {
            val regex =
                Pattern.compile("(http://www.|https://www.|http://|https://)?[a-zA-Z\\d]+([\\-.][a-zA-Z\\d]+)*\\.[a-z]{2,5}(:\\d{1,5})?(/.*)?$")
            val regexMatcher = regex.matcher(it)
            return regexMatcher.matches()
        } catch (ex: PatternSyntaxException) {
            ex.printStackTrace()
            return false
        }
    } ?: false
}

fun Int?.default(defaultValue: Int? = 0): Int {
    return this ?: run { defaultValue ?: 0 }
}

fun Int?.pxToDp(context: Context): Float{
    return this?.let {
        it / (context.resources.displayMetrics.densityDpi / 160f)
    }?: 0f

//    return this?.let {
//        (context.resources.displayMetrics.density * it).toInt()
//    }?: 0f
}

fun Float?.dpToPx(context: Context): Int {
    return this?.let { dp->
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }?: 0
}

fun Float?.spToPx(context: Context): Int {
    return this?.let { dp-> TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, context.resources.displayMetrics).toInt() }?: 0
}

fun Float?.dpToSp(context: Context): Int {
    return this?.let { (it.dpToPx(context) / context.resources.displayMetrics.scaledDensity).toInt() } ?:0
}

fun Long?.default(defaultValue: Long? = 0): Long {
    return this ?: run { defaultValue ?: 0 }
}

fun Float?.default(defaultValue: Float? = 0f): Float {
    return this ?: run { defaultValue ?: 0f }
}

fun Double?.default(defaultValue: Double? = 0.0 ): Double{
    return this ?: run { defaultValue ?: 0.0 }
}

fun Boolean?.default(defaultValue: Boolean? = false): Boolean {
    return this ?: run { defaultValue ?: false }
}

fun <T> List<T>?.isNonEmpty(): Boolean {
    return this?.let { isNotEmpty() && size > 0 } ?: false
}

fun <T> List<T>?.isEmpty() = !this.isNonEmpty()

fun <T> List<T>?.isValidPosition(position: Int): Boolean {
    return this?.let { it.isNonEmpty() && position >= 0 && position < size } ?: false
}

fun <T> List<T>?.isValidItem(position: Int): List<T> {
    return this?.takeIf { isValidPosition(position) }?.let { this } ?: ArrayList()
}

fun <T> List<T>?.default(items: List<T>? = ArrayList()): List<T> {
    return this?.takeIf { it.isNonEmpty() } ?: run { items ?: ArrayList() }
}

fun randomBetween(min: Int, max: Int): Int {
    return min + (Math.random() * (max - min)).toInt()
}
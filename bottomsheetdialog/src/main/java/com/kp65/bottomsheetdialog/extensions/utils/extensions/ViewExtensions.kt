package com.kp65.bottomsheetdialog.extensions.utils.extensions

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView

fun EditText?.getValue(): String {
    return this?.let {
        if (text != null && text.toString().isNonEmpty()) text.toString() else ""
    } ?: run {
        ""
    }
}


fun TextView?.getValue(): String {
    return this?.let {
        if (text != null && text.toString().isNonEmpty()) text.toString() else ""
    } ?: run {
        ""
    }
}

/**
 * Get gradient drawable from textview
 * required : textview is not null
 * */
fun TextView?.gradientDrawable(): GradientDrawable {
    return this?.let {
        background as GradientDrawable
    } ?: kotlin.run { GradientDrawable() }
}


/**
 * View hide and show condition wise
 * if view is show pass true
 * if view is hide pass false
 * */
fun View?.hideShow(isShow: Boolean) {
    this?.let { visibility = if (isShow) View.VISIBLE else View.GONE }
}

fun View?.invisibleShow(isShow: Boolean) {
    this?.let { visibility = if (isShow) View.VISIBLE else View.INVISIBLE }
}

/**
 * View hide easily
 * if view is not gone that time it gone
 * */
fun View?.hide() {
    this?.let { visibility = if (visibility != View.GONE) View.GONE else visibility }
}

/**
 * View show easily
 * if view is not visible that time it visible
 * */
fun View?.show() {
    this?.let { visibility = if (visibility != View.VISIBLE) View.VISIBLE else visibility }
}

/**
 * View invisible easily
 * if view is not invisible that time it work
 * */
fun View?.invisible() {
    this?.let { visibility = if (visibility != View.INVISIBLE) View.INVISIBLE else visibility }
}

/**
 * SET VIEW MARGIN
 * @param left Type Int
 * @param top Type Int
 * @param right Type Int
 * @param bottom Type Int
 * */
fun View?.setMargin(left: Int, top: Int, right: Int, bottom: Int) {
    this?.let {
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(left, top, right, bottom)
        it.layoutParams = params
    }
}

/**
 * Change View Drawable Background
 * */
fun View?.changeBackgroundDrawableColor(color: Int) {
    this?.let {
        background.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.LIGHTEN)
        //background.setColorFilter(color, PorterDuff.Mode.LIGHTEN)
    }
}

/**
 * Manage View Enable Disable
 * */
fun View?.isEnable(isEnable: Boolean) {
    this?.let {
        it.isClickable = isEnable
        it.isFocusable = isEnable
        it.alpha = if (isEnable) 1.0f else 0.5f
        it.isLongClickable = isEnable
        it.isEnabled = isEnable
    }
}

/*
* Hide Keyboard
* */
fun View?.hideKeyBoard() {
    this?.let {
        val imm = it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}
package com.kp65.bottom_sheet_dialog.utils.extensions

import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.GradientDrawable
import android.os.ResultReceiver
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


fun EditText?.getValue(): String {
    return this?.let {
        if (text != null && text.toString().isNonEmpty()) text.toString().default() else ""
    } ?: ""
}

/**
 * Scroll Edit Text
 */
fun EditText.setScrollable() {
    this.setOnTouchListener { v: View, event: MotionEvent ->
        if (this.hasFocus()) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            if (event.action and MotionEvent.ACTION_MASK == MotionEvent.ACTION_SCROLL) {
                v.parent.requestDisallowInterceptTouchEvent(false)
                return@setOnTouchListener true
            }
        }
        false
    }
}



/**
 * get value form text view
 * */
fun TextView?.getValue(): String {
    return this?.let {
        if (text != null && text.toString().isNonEmpty()) text.toString() else ""
    } ?: run {
        ""
    }
}

/**
 * Related To Color
 */
fun TextView?.setLabel(viewColor: GradientDrawable, color: Int, text: String) {
    this?.let {
        viewColor.setColor(ContextCompat.getColor(it.context, color))
        it.text = text
    }
}




/**
 * Set icon tint color
 * @param color == R.color.colorPrimary
 */
fun TextView?.setIconTintColor(color: Int) {
    this?.let {
        it.compoundDrawables.forEach { drawable ->
            if (drawable != null) {
                drawable.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(it.context, color), PorterDuff.Mode.SRC_IN
                )
            }
        }
    }
}

/**
 * Set icon tint color in image view
 * @param colorId == R.color.colorPrimary
 * */
fun ImageView?.setIconTintColor(colorId: Int) {
    this?.let {
        it.setColorFilter(ContextCompat.getColor(it.context, colorId), PorterDuff.Mode.SRC_IN)
    }
}


/**
 * Get gradient drawable from textview
 * required : textview is not null
 * */
fun View?.gradientDrawable(): GradientDrawable {
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
 * View is visible
 * */
fun View?.isShow(): Boolean {
    return this?.let { visibility == View.VISIBLE } ?: run { false }
}

/**
 * View is gone
 * */
fun View?.isHide(): Boolean {
    return this?.let { visibility == View.GONE } ?: run { false }
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

/**
 * show toast
 * */
fun View?.showToast(message: String) {
    this?.context.showToast(message)
}

/**
 * Show Keyboard
 * */
fun View?.showKeyboard(resultReceiver: ResultReceiver? = null) {
    this?.let {
        val config: Configuration = it.context.resources.configuration
        if (config.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            val imm =
                it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (resultReceiver != null) imm.showSoftInput(
                it,
                InputMethodManager.SHOW_IMPLICIT,
                resultReceiver
            )
            else imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}



/**
 * manage drawable
 * */

fun View?.setCustomBg(bgColor:Int? = null, strokeWidth:Int = 0, strokeColor:Int? = null, radius : Float = 0f){
    this?.let {
        val context = it.context
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        bgColor?.let { shape.setColor(ContextCompat.getColor(context,bgColor) ) }
        strokeColor?.takeIf { strokeWidth != 0 }?.let {
            shape.setStroke(strokeWidth,ContextCompat.getColor(context,strokeColor))
        }
        if(radius != 0f) shape.cornerRadius = radius
        it.background = shape
    }
}


/**
 *
 * */
fun Dialog?.visibility(isShow: Boolean) {
    if (isShow) this?.show()
    else this?.dismiss()
}

fun Dialog?.showToast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    this?.let {
        it.context.showToast(msg, length)
    }
}

/**
 * show toast message
 * */
fun Context?.showToast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    this?.takeIf { msg.isNonEmpty() }?.let {
        Toast.makeText(it, msg.default(), length).show()
    }
}


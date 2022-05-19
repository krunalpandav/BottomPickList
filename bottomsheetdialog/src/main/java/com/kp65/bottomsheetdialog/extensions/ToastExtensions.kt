package com.kp65.bottomsheetdialog.extensions

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

private var toast: Toast? = null

fun Context?.showToast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    toast?.cancel()
    this?.takeIf { msg != null }?.let {
        toast = Toast.makeText(it, msg, length)
        toast?.show()
    }
}

fun Activity?.showToast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    this?.let {
        (it as Context).showToast(msg, length)
    }
}

fun Fragment?.showToast(msg: String?, length: Int = Toast.LENGTH_SHORT) {
    this?.context?.let {
        it.showToast(msg, length)
    }
}
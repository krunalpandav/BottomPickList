package com.kp65.bottom_sheet_dialog.utils

import android.util.Log

object LogUtils {

    fun print(tag: String? = "Common", text: String? = "") {
        Log.i(tag, "" + text)
    }

    fun error(tag: String? = "Common", text: String? = "") {
        Log.e(tag, "" + text)
    }
}
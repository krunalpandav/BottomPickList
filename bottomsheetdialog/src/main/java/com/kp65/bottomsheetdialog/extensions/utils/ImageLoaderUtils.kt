package com.kp65.bottomsheetdialog.extensions.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.kp65.bottomsheetdialog.extensions.utils.extensions.hide
import com.kp65.bottomsheetdialog.extensions.utils.extensions.isNonEmpty
import com.kp65.bottomsheetdialog.extensions.utils.extensions.show

object ImageLoaderUtils {
    @JvmStatic
    fun loadProfileImageI(
        context: Context?,
        imgFile: String?,
        assignedTo: String?,
        iv: ImageView,
        tv: TextView,
        strName: String?,
        ic_mini_plash_holder: Int,
        resizeWidth: Int,
        resizeHeight: Int,
        requestOptions: RequestOptions?
    ) {
        try {
            if (imgFile.isNonEmpty()) {
                iv.show()
                tv.hide()
                Glide.with(context!!)
                    .load(imgFile)
                    .apply(requestOptions!!)
                    .override(resizeWidth, resizeHeight).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(iv)
            } else {
                iv.hide()
                tv.show()
                loadImageName(tv, strName)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadImageName(tv: TextView, strName: String?) {
        if (strName == null) tv.text = "" else {
            val str = strName.split(" ".toRegex()).toTypedArray()
            val strFirst = if (str.isNotEmpty() && str[0].isNotEmpty()) str[0].trim { it <= ' ' }
                .substring(0, 1) else ""
            val strSec = if (str.size > 1 && str[1].isNotEmpty()) str[1].trim { it <= ' ' }
                .substring(0, 1) else ""
            val name = strFirst.uppercase() + strSec.uppercase()
            tv.text = name
        }
    }
}
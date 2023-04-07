package com.kp65.bottom_sheet_dialog.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.kp65.bottom_sheet_dialog.R
import com.kp65.bottom_sheet_dialog.databinding.ViewProfileImageBinding
import com.kp65.bottom_sheet_dialog.utils.extensions.hideShow
import com.kp65.bottom_sheet_dialog.utils.extensions.invisibleShow
import com.kp65.bottom_sheet_dialog.utils.extensions.isEmpty
import com.kp65.bottom_sheet_dialog.utils.extensions.isNonEmpty
import com.kp65.bottom_sheet_dialog.utils.extensions.isValidUrl

class ProfileImageView(context: Context?, attrs: AttributeSet?) : FrameLayout(context!!, attrs!!) {

    private lateinit var binding: ViewProfileImageBinding

    private fun init() {
        val inflater = LayoutInflater.from(context)
        removeAllViews()
        binding = ViewProfileImageBinding.inflate(inflater,this,true)
    }

    fun setProfile(userName:String,userImageUrl:String){
        loadProfilePhoto(
            context,
            userImageUrl, binding.image,
            userName, binding.name,
            R.drawable.ic_mini_plash_holder,
            context.resources.getDimension(R.dimen.margin_25dp).toInt(),
            context.resources.getDimension(R.dimen.margin_25dp).toInt(),
        )
    }

    init {
        init()
    }

    private fun loadProfilePhoto(
        context: Context,
        imageUrl: String,
        imageView: ImageView,
        userName: String,
        textView: TextView,
        placeholder: Int,
        resizeWidth: Int,
        resizeHeight: Int,
    ) {
        val isShowImage = imageUrl.isNonEmpty() && imageUrl.isValidUrl()
        imageView.hideShow(isShowImage)
        textView.hideShow(!isShowImage)
        try {
            imageView.takeIf { isShowImage }?.let {
                Glide.with(context)
                    .load(imageUrl)
                    .placeholder(placeholder)
                    .override(resizeWidth, resizeHeight)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            loadImageName(imageView,textView, userName)
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                    })
                    .into(it)
            } ?: run {
                loadImageName(imageView,textView, userName)
            }
        } catch (e: java.lang.Exception) {
            LogUtils.error(Constants.TAG, e.localizedMessage)
        }
    }

    private fun loadImageName(imageView: ImageView,tv: TextView, strName: String?) {
        strName?.takeIf { it.isNonEmpty() }?.let {
            val str = strName.split(" ".toRegex()).toTypedArray()
            val strFirst = if (str.isNotEmpty() && str[0].isNotEmpty()) str[0].trim { it <= ' ' }
                .substring(0, 1) else ""
            val strSec = if (str.size > 1 && str[1].isNotEmpty()) str[1].trim { it <= ' ' }
                .substring(0, 1) else ""
            val name = strFirst.uppercase() + strSec.uppercase()
            tv.text = name
        }

        imageView.invisibleShow(strName.isEmpty())
        tv.invisibleShow(strName.isNonEmpty())
    }
}
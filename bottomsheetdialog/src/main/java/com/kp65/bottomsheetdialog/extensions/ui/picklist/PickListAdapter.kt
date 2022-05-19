package com.kp65.bottomsheetdialog.extensions.ui.picklist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.kp65.bottomsheetdialog.R
import com.kp65.bottomsheetdialog.databinding.RowPickListBinding
import com.kp65.bottomsheetdialog.extensions.data.datasource.interfaces.PickListListener
import com.kp65.bottomsheetdialog.extensions.data.model.DataPickList
import com.kp65.bottomsheetdialog.extensions.utils.Constants.SEL_ITEM_ID
import com.kp65.bottomsheetdialog.extensions.utils.Constants.SEL_ITEM_KEY
import com.kp65.bottomsheetdialog.extensions.utils.Constants.SEL_ITEM_POSITION
import com.kp65.bottomsheetdialog.extensions.utils.ImageLoaderUtils.loadProfileImageI
import com.kp65.bottomsheetdialog.extensions.utils.extensions.*

@SuppressLint("NonConstantResourceId")
class PickListAdapter(
    private val context: Context,
    private var list: MutableList<DataPickList>?,
    selectedValue: String,
    private val onItemClick: PickListListener?
) :
    RecyclerView.Adapter<PickListAdapter.PickVewHolder>() {
    private var selectedValue = ""

    fun updateList(list: MutableList<DataPickList>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickVewHolder {
        return PickVewHolder(
            RowPickListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PickVewHolder, position: Int) {
        list?.let { items ->
            val (id, title, key, profileImage, isShowPI, returnType) = items[position]

            if (isShowPI == true) {
                holder.binding.lytUser.show()

                loadProfileImageI(
                    context,
                    profileImage, title,
                    holder.binding.ivUser, holder.binding.tvUserName, title,
                    R.drawable.ic_mini_plash_holder,
                    context.resources.getDimension(R.dimen.margin_20dp).toInt(),
                    context.resources.getDimension(R.dimen.margin_20dp).toInt(),
                    RequestOptions.circleCropTransform()
                )
            } else {
                holder.binding.lytUser.hide()
            }
            holder.binding.tvName.text = title ?: "No Title"

            selectedValue = when (returnType) {
                SEL_ITEM_ID -> selectedValue.default("-1")
                SEL_ITEM_POSITION -> selectedValue.default("-1")
                SEL_ITEM_KEY -> selectedValue.default("")
                else -> selectedValue
            }

            val drawable = if (returnType == SEL_ITEM_ID) {
                if (id == selectedValue && selectedValue != "-1") ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_selected
                ) else null
            } else if (returnType == SEL_ITEM_POSITION) {
                if (position == selectedValue.toInt() && selectedValue != "-1") ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_selected
                ) else null
            } else if (returnType == SEL_ITEM_KEY) {
                if (key == selectedValue && selectedValue.isNonEmpty()) ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_selected
                ) else null
            } else {
                null
            }

            holder.binding.tvName.let {
                it.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
                it.setTextColor(
                    ContextCompat.getColor(
                        it.context,
                        if (drawable != null) R.color.colorPrimary else R.color.colorProfileTitle
                    )
                )
            }
        }
    }

    inner class PickVewHolder(val binding: RowPickListBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {


        override fun onClick(v: View) {
            val position = adapterPosition
            if (list.isValidPosition(position)) {
                val (id, _, key, _, _, returnType) = list!![position]
                if (v == itemView) {
                    if (onItemClick != null) {
                        when (returnType) {
                            SEL_ITEM_ID -> {
                                onItemClick.onSelectedItem(id, returnType)
                            }
                            SEL_ITEM_POSITION -> {
                                onItemClick.onSelectedItem("" + position, returnType)
                            }
                            SEL_ITEM_KEY -> {
                                onItemClick.onSelectedItem(key, returnType)
                            }
                        }
                    }
                }
            }
        }

        init {
            itemView.setOnClickListener(this)

        }
    }

    init {
        this.selectedValue = selectedValue
    }
}
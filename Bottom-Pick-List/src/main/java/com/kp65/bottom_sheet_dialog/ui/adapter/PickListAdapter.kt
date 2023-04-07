package com.kp65.bottom_sheet_dialog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kp65.bottom_sheet_dialog.R
import com.kp65.bottom_sheet_dialog.databinding.RowPickListBinding
import com.kp65.bottom_sheet_dialog.listener.PeckListListener
import com.kp65.bottom_sheet_dialog.model.DataPickList
import com.kp65.bottom_sheet_dialog.utils.Constants
import com.kp65.bottom_sheet_dialog.utils.extensions.default
import com.kp65.bottom_sheet_dialog.utils.extensions.hideShow
import com.kp65.bottom_sheet_dialog.utils.extensions.isNonEmpty
import com.kp65.bottom_sheet_dialog.utils.extensions.isValidPosition

class PickListAdapter(
    private var list: MutableList<DataPickList>?,
    selectedValue: String,
    private val onItemClick: PeckListListener?
) : RecyclerView.Adapter<PickListAdapter.VewHolder>() {
    private var selectedValue = ""

    init {
        this.selectedValue = selectedValue
    }

    fun updateList(list: MutableList<DataPickList>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list?.size.default()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VewHolder(
        RowPickListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(viewHolder: VewHolder, position: Int) {
        list?.takeIf { it.isValidPosition(position) }?.get(position)?.let {
            viewHolder.setData(it,position)
        }
    }

    inner class VewHolder(private val binding: RowPickListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(data : DataPickList,position: Int){
            setDataInView(binding,data,position)
        }

        init {
            itemView.setOnClickListener{ setListener(adapterPosition) }
        }
    }

    private fun setListener(position: Int) {
        list?.takeIf { it.isValidPosition(position) }?.get(position)?.let {data->
            when (val returnType = data.returnType.default()) {
                Constants.SEL_ITEM_ID -> onItemClick?.onSelectedItem(
                    data.id.default("0"),
                    returnType.default()
                )

                Constants.SEL_ITEM_POSITION -> onItemClick?.onSelectedItem(
                    position.toString(),
                    returnType.default()
                )

                Constants.SEL_ITEM_KEY -> onItemClick?.onSelectedItem(
                    data.key.default(),
                    returnType.default()
                )

                else -> {}
            }
        }
    }

    private fun setDataInView(binding: RowPickListBinding, data: DataPickList,position: Int) {
        val (id, title, key, profileImage, isShowPI, returnType) = data

        val context = binding.root.context

        binding.userProfile.hideShow(isShowPI.default())
        if(isShowPI.default())
            binding.userProfile.setProfile(title.default(),profileImage.default())

        binding.tvName.text = title.default("No Title")

        selectedValue = when (returnType) {
            Constants.SEL_ITEM_ID -> selectedValue.default("-1")
            Constants.SEL_ITEM_POSITION -> selectedValue.default("-1")
            Constants.SEL_ITEM_KEY -> selectedValue.default("")
            else -> selectedValue
        }

        val drawable = if (returnType == Constants.SEL_ITEM_ID) {
            if (id == selectedValue && selectedValue != "-1") ContextCompat.getDrawable(
                context,
                R.drawable.ic_check_circle
            ) else null
        } else if (returnType == Constants.SEL_ITEM_POSITION) {
            if (position == selectedValue.toInt() && selectedValue != "-1") ContextCompat.getDrawable(
                context,
                R.drawable.ic_check_circle
            ) else null
        } else if (returnType == Constants.SEL_ITEM_KEY) {
            if (key == selectedValue && selectedValue.isNonEmpty()) ContextCompat.getDrawable(
                context,
                R.drawable.ic_check_circle
            ) else null
        } else {
            null
        }

        binding.tvName.let {
            it.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
            it.setTextColor(
                ContextCompat.getColor(
                    it.context,
                    if (drawable != null) R.color.color_green else R.color.color_profile_title
                )
            )
        }
    }
}
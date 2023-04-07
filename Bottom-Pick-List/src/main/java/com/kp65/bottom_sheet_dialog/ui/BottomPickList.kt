package com.kp65.bottom_sheet_dialog.ui

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kp65.bottom_sheet_dialog.R
import com.kp65.bottom_sheet_dialog.databinding.PopupPickListBinding
import com.kp65.bottom_sheet_dialog.listener.PeckListListener
import com.kp65.bottom_sheet_dialog.listener.SelectedDataListener
import com.kp65.bottom_sheet_dialog.model.DataPickList
import com.kp65.bottom_sheet_dialog.ui.adapter.PickListAdapter
import com.kp65.bottom_sheet_dialog.utils.Constants
import com.kp65.bottom_sheet_dialog.utils.LineItemDecoration
import com.kp65.bottom_sheet_dialog.utils.extensions.default
import com.kp65.bottom_sheet_dialog.utils.extensions.hideKeyBoard
import com.kp65.bottom_sheet_dialog.utils.extensions.hideShow
import com.kp65.bottom_sheet_dialog.utils.extensions.isEnable
import com.kp65.bottom_sheet_dialog.utils.extensions.isNonEmpty
import com.kp65.bottom_sheet_dialog.utils.extensions.showToast
import java.util.Locale

class BottomPickList(
    private val context: Context,
    private val list: MutableList<DataPickList>?,
    private val title: String,
    private val selectedValue: String,
    private val selectedDataListener: SelectedDataListener? = null,
    private val isAddItemIfNotFoundInSearch :Boolean? = false
) {

    init {
        show()
    }

    /**
     * Show Bottom Sheet Popup
     * */
    private fun show() {
        if (list.isNonEmpty()) {
            create(context, list, title, selectedValue,isAddItemIfNotFoundInSearch.default(), selectedDataListener)
        } else {
            context.showToast("Selected item related data not found...")
        }
    }


    private fun create(
        context: Context,
        list: MutableList<DataPickList>?,
        title: String,
        selectedValue: String,
        isAddIfNotFound :Boolean,
        selectedDataListener: SelectedDataListener?
    ) {
        val dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)

        val binding = PopupPickListBinding.inflate(LayoutInflater.from(context))

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        //dialog.setCancelable(false)
        dialog.setCancelable(true)
        dialog.setOnDismissListener { dialog.dismiss() }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        dialog.behavior.peekHeight = 1200
        dialog.show()


        binding.tvTitle.text = title

        binding.tvCancel.setOnClickListener {
            binding.etSearch.isEnable(false)
            binding.etSearch.hideKeyBoard()
            dialog.dismiss()
        }



        binding.rv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rv.addItemDecoration(LineItemDecoration(context, LinearLayout.VERTICAL))
        binding.rv.itemAnimator = DefaultItemAnimator()

        binding.etSearch.isEnable((list.default()).isNotEmpty())
        binding.etSearch.hideShow(list?.size.default() > 5)
        val adapter = PickListAdapter(
            list,
            selectedValue,
            object : PeckListListener {
                override fun onSelectedItem(passValue: String, type: Int) {
                    selectedDataListener?.let {
                        when (type) {
                            Constants.SEL_ITEM_ID -> it.getSelectedID(
                                passValue.default("0").toInt()
                            )

                            Constants.SEL_ITEM_POSITION -> it.getSelectedPosition(
                                passValue.default("0").toInt()
                            )

                            Constants.SEL_ITEM_KEY -> it.getSelectedKey(passValue)

                        }
                    }
                    binding.etSearch.isEnable(false)
                    binding.etSearch.hideKeyBoard()
                    dialog.dismiss()
                }
            }
        )

        binding.rv.adapter = adapter
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString(), adapter, list, binding.rv, binding.tvErrMsg,isAddIfNotFound)
            }
        })

        dialog.setOnDismissListener {
            binding.etSearch.isEnable(false)
            binding.etSearch.hideKeyBoard()
            dialog.dismiss()
        }
    }

    private var newItem : DataPickList?=null

    private fun filter(
        text: String,
        adapter: PickListAdapter,
        list: List<DataPickList>? = ArrayList(),
        rv: RecyclerView,
        tvNoData: TextView,
        isAddIfNotFound :Boolean
    ) {
        list?.let { searchList ->
            val temp: MutableList<DataPickList> = ArrayList()
            var returnType :Int? = null
            searchList.forEach {
                returnType = it.returnType
                val title = it.title.default().lowercase()
                if (title.contains(text.lowercase(Locale.getDefault()))) {
                    temp.add(it)
                }
            }


            if(isAddIfNotFound && temp.isEmpty()) {
                newItem = DataPickList(title = text, key = text, returnType = returnType)
                newItem?.let { temp.add(it) }
            }
            else {
                rv.hideShow(temp.isNotEmpty())
                tvNoData.hideShow(temp.isEmpty())
            }

            //update recyclerview
            adapter.updateList(temp)
        }
    }
}
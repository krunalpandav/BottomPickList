package com.kp65.bottomsheetdialog.extensions.ui.picklist

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kp65.bottomsheetdialog.R
import com.kp65.bottomsheetdialog.extensions.data.datasource.interfaces.PickListListener
import com.kp65.bottomsheetdialog.extensions.data.datasource.interfaces.SelectedIdListener
import com.kp65.bottomsheetdialog.extensions.data.model.DataPickList
import com.kp65.bottomsheetdialog.extensions.showToast
import com.kp65.bottomsheetdialog.extensions.utils.Constants.SEL_ITEM_ID
import com.kp65.bottomsheetdialog.extensions.utils.Constants.SEL_ITEM_KEY
import com.kp65.bottomsheetdialog.extensions.utils.Constants.SEL_ITEM_POSITION
import com.kp65.bottomsheetdialog.extensions.utils.LineItemDecoration
import com.kp65.bottomsheetdialog.extensions.utils.extensions.hideKeyBoard
import com.kp65.bottomsheetdialog.extensions.utils.extensions.hideShow
import com.kp65.bottomsheetdialog.extensions.utils.extensions.isEnable
import com.kp65.bottomsheetdialog.extensions.utils.extensions.isNonEmpty
import java.util.*

object BottomSheetPickList {

    /**
     * Show Bottom Sheet Popup
     * */
    @JvmStatic
    fun show(
        context: Context,
        list: MutableList<DataPickList>?,
        title: String,
        selectedValue: String,
        selectedIdListener: SelectedIdListener?
    ) {
        if (list.isNonEmpty()) {
            create(context, list, title, selectedValue, selectedIdListener)
        } else {
            context.showToast("Selected item related data not found...")
        }
    }

    private fun create(
        context: Context,
        list: MutableList<DataPickList>?,
        title: String,
        selectedValue: String,
        selectedIdListener: SelectedIdListener?
    ) {
        val dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        val view = LayoutInflater.from(context).inflate(R.layout.popup_pick_list, null)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        dialog.behavior.peekHeight = 1200
        dialog.show()

        val etSearch = view.findViewById<EditText>(R.id.et_search)

        (view.findViewById<View>(R.id.tv_title) as TextView).text = title

        (view.findViewById<View>(R.id.tv_cancel) as TextView).setOnClickListener {
            etSearch.isEnable(false)
            etSearch.hideKeyBoard()
            dialog.dismiss()
        }
        val tvNoData = view.findViewById<TextView>(R.id.tv_err_msg)
        val rv: RecyclerView = view.findViewById(R.id.rv)

        rv.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv.addItemDecoration(LineItemDecoration(context, LinearLayout.VERTICAL))
        rv.itemAnimator = DefaultItemAnimator()

        etSearch.isEnable((list?: ArrayList()).isNotEmpty())
        etSearch.hideShow((list?.size ?: 0) > 5)
        val adapter = PickListAdapter(
            context,
            list,
            selectedValue,
            object : PickListListener {
                override fun onSelectedItem(passValue: String?, type: Int) {
                    selectedIdListener?.let {
                        when (type) {
                            SEL_ITEM_ID -> it.getSelectedID((passValue ?: "0").toInt())
                            SEL_ITEM_POSITION -> it.getSelectedPosition(
                                (passValue ?: "0").toInt()
                            )
                            SEL_ITEM_KEY -> it.getSelectedKey(passValue)

                        }
                    }
                    etSearch.isEnable(false)
                    etSearch.hideKeyBoard()
                    dialog.dismiss()
                }
            }
        )

        rv.adapter = adapter
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString(), adapter, list, rv, tvNoData)
            }
        })

        dialog.setOnDismissListener {
            etSearch.isEnable(false)
            etSearch.hideKeyBoard()
            dialog.dismiss()
        }
    }

    private fun filter(
        text: String,
        adapter: PickListAdapter,
        list: List<DataPickList>? = ArrayList(),
        rv: RecyclerView,
        tvNoData: TextView
    ) {
        list?.let { searchList ->
            val temp: MutableList<DataPickList> = ArrayList()
            for (item in searchList) {
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches
                val title = (item.title ?: "").lowercase()
                if (title.contains(text.lowercase(Locale.getDefault()))) {
                    temp.add(item)
                }
            }
            rv.hideShow(temp.isNotEmpty())
            tvNoData.hideShow(temp.isEmpty())

            //update recyclerview
            adapter.updateList(temp)
        }
    }
}
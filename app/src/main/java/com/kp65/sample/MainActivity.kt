package com.kp65.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kp65.bottom_sheet_dialog.listener.SelectedDataListener
import com.kp65.bottom_sheet_dialog.model.DataPickList
import com.kp65.bottom_sheet_dialog.ui.BottomPickList
import com.kp65.bottom_sheet_dialog.utils.Constants
import com.kp65.bottom_sheet_dialog.utils.extensions.default
import com.kp65.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private var pickList : MutableList<DataPickList> = ArrayList()

    private val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        manageFunction()

    }

    private fun manageFunction() {
        createPickList()
        setListener()
    }

    private fun createPickList() {
        if(pickList.isNotEmpty()) pickList.clear()
        pickList = ArrayList()

        for (i in 1..15) {
            pickList.add(DataPickList(i.toString(),"Item $i", returnType = Constants.SEL_ITEM_ID))
        }


    }

    private fun setListener() {
        binding.btnDialog.setOnClickListener{
            openPickList()
        }
    }

    private fun openPickList(){
        BottomPickList(this,pickList,"Test Item","",object : SelectedDataListener{
            override fun getSelectedID(selectedID: Int) {
                val item = pickList.find { it.id.default("0").toInt() == selectedID }
                item?.let {
                    val label = getString(R.string.lb_selected_value)
                    binding.tvSelectedItem.text = label.replace("_item_",it.title.default())
                }
            }
        })
    }
}
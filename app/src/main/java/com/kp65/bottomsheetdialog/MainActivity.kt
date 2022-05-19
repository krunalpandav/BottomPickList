package com.kp65.bottomsheetdialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kp65.bottomsheetdialog.extensions.showToast

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val btn_dialog : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListener()
    }

    private fun setListener() {
        btn_dialog?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        showToast("Test Working")
    }
}
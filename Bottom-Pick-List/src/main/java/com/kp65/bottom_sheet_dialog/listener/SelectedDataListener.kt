package com.kp65.bottom_sheet_dialog.listener

interface SelectedDataListener {
    fun getSelectedID(selectedID: Int) {}
    fun getSelectedKey(selectedKey: String) {}
    fun getSelectedPosition(selectedPosition: Int) {}
}
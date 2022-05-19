package com.kp65.bottomsheetdialog.extensions.data.datasource.interfaces;

public interface SelectedIdListener {
    default void getSelectedRelatedToID(String relatedToId){}

    default void getSelectedAssignTo(int position,String assignToId,String workDate){}

    default void getSelectedApprovedBy(int position,String approvedById){}

    default void getSelectedID(int selectedID){}

    default void getSelectedKey(String selectedKey){}

    default void getSelectedPosition(int selectedPosition){}

    default void isUpdatePriority(boolean isUpdate){}
}

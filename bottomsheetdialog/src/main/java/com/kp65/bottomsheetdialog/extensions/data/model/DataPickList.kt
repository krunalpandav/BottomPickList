package com.kp65.bottomsheetdialog.extensions.data.model

import android.os.Parcel
import android.os.Parcelable


data class DataPickList(
    var id: String? = "0",
    var title: String? = "",
    var key: String? = "",
    var profileImage: String? = "",
    var isShowPI: Boolean? = false,
    var returnType: Int
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(key)
        parcel.writeString(profileImage)
        parcel.writeValue(isShowPI)
        parcel.writeInt(returnType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataPickList> {
        override fun createFromParcel(parcel: Parcel): DataPickList {
            return DataPickList(parcel)
        }

        override fun newArray(size: Int): Array<DataPickList?> {
            return arrayOfNulls(size)
        }
    }
}
package com.android.wms.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(
    @SerializedName("account", alternate = ["PHONE"])
    var account: String = "",
    var pwd: String = "",
    ) : Parcelable
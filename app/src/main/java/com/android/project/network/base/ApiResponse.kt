package com.android.project.network.base

import com.google.gson.annotations.SerializedName


data class ApiResponse<T>(
    var code: Int,
    @SerializedName("msg", alternate = ["message"])
    var msg: String,
    var data: T) : BaseResponse<T>() {

    //请求结果(200:成功;其他:失败)
    override fun isSuccess() = code == 200

    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = msg

}
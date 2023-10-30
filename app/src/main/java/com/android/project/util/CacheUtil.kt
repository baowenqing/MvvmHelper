package com.android.project.util

import com.android.wms.model.UserInfo
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import com.win.mvvmhelper.ext.toEntity
import com.win.mvvmhelper.ext.toJsonStr


object CacheUtil {

    private var mmkv: MMKV = MMKV.mmkvWithID("app")



    fun saveToken(token: String) {
        mmkv.encode("token", token)
    }

    fun getToken(): String {
        return mmkv.decodeString("token", "")?:""
    }



    fun saveAccount(phone: String) {
        mmkv.encode("account", phone)
    }

    fun getAccount(): String {
        return mmkv.decodeString("account", "")?:""
    }

    fun savePassword(phone: String) {
        mmkv.encode("password", phone)
    }

    fun getPassword(): String {
        return mmkv.decodeString("password", "")?:""
    }

    fun savePrinterAddress(address: String) {
        mmkv.encode("PrinterAddress", address)
    }

    fun getPrinterAddress(): String {
        return mmkv.decodeString("PrinterAddress", "")?:""
    }



    fun saveUserInfo(userInfo: UserInfo?) {
        mmkv.encode("userInfo", userInfo.toJsonStr())
    }

    fun getUserInfo(): UserInfo? {
        val userInfoStr = mmkv.getString("userInfo", "")!!
        return    userInfoStr.toEntity<UserInfo>()
    }



    fun clearCache() {
//        mmkv.clearAll()
        saveUserInfo(null)
        saveToken("")
    }


    inline fun <reified T> genericType() = object : TypeToken<T>() {}.type
}
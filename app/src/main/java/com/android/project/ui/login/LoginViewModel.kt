package com.android.project.ui.login

import androidx.lifecycle.MutableLiveData
import com.android.project.network.NetUrl
import com.android.project.network.UserRepository
import com.android.project.util.CacheUtil
import com.android.wms.model.UserInfo
import com.blankj.utilcode.util.LogUtils
import com.win.mvvmhelper.base.BaseViewModel
import com.win.mvvmhelper.ext.rxHttpRequest
import com.win.mvvmhelper.net.LoadingType


class LoginViewModel : BaseViewModel() {

    //登录请求信息
    val loginData = MutableLiveData<UserInfo>().apply {
        this.value = UserInfo("R0194", "123")
    }

    /**
     * 登录
     */
    fun login(success: () -> Unit) {
        CacheUtil.saveToken("")
        rxHttpRequest {
            onRequest = {
                val token = UserRepository.login(loginData.value!!).await()
//                CacheUtil.saveToken(token)
                success.invoke()

            }
            loadingType = LoadingType.LOADING_DIALOG //选传 默认为 LoadingType.LOADING_NULL
            loadingMessage = "正在登录中....." // 选传
            requestCode = NetUrl.LOGIN // 选传，如果要判断接口错误业务的话必传
        }
    }


}


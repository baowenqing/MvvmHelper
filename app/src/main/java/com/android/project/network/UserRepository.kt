package com.android.project.network

import com.android.project.network.base.ApiPagerResponse
import com.android.wms.model.UserInfo
import com.win.mvvmhelper.ext.md5
import rxhttp.wrapper.coroutines.Await
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toAwaitResponse

/**
 * 作者　: hegaojian
 * 时间　: 2020/11/2
 * 描述　: 数据仓库
 */
object UserRepository {

    /**
     * 登录
     */
    fun login(userInfo: UserInfo ): Await<UserInfo> {
        userInfo.pwd=   userInfo.pwd.md5().lowercase()
        return RxHttp.postBody(NetUrl.LOGIN)
            .setBody(userInfo)
            .toAwaitResponse()
    }



}


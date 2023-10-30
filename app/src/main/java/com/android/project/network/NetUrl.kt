package com.android.project.network

import rxhttp.wrapper.annotation.DefaultDomain

/**
 * 作者　: hegaojian
 * 时间　: 2021/6/9
 * 描述　:
 */
object NetUrl {

    // 服务器请求成功的 Code值
    const val SUCCESS_CODE = 0

    @DefaultDomain //设置为默认域名
    const val DEV_URL = "http://192.168.2.69:30079/api/"

    //登录
    const val LOGIN = "ros-base/auth/login"

}
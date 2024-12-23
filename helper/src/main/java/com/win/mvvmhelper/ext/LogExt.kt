package com.win.mvvmhelper.ext

import com.blankj.utilcode.util.LogUtils
import com.hjq.toast.ToastUtils




fun Any?.logE(tag: String? = null) =
    LogUtils.eTag(tag,this)




fun String?.toast(){
    ToastUtils.show(this)
}

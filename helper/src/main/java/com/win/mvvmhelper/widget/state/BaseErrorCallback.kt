package com.win.mvvmhelper.widget.state

import com.win.mvvmhelper.R
import com.win.mvvmhelper.loadsir.callback.Callback

/**
 * 作者　: hegaojian
 * 时间　: 2021/6/8
 * 描述　:
 */
class BaseErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_base_error
    }

}
package com.android.project.widget.callback

import android.content.Context
import android.view.View
import com.android.project.R
import com.win.mvvmhelper.loadsir.callback.Callback

/**
 * 作者　: hegaojian
 * 时间　: 2023/3/28
 * 描述　:
 */
class LoadingCallback :Callback() {

    override fun onCreateView() = R.layout.layout_loading

    override fun onReloadEvent(context: Context?, view: View?) = true
}
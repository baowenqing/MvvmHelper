package com.android.project.base

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.android.project.R
import com.hjq.bar.TitleBar
import com.win.mvvmhelper.base.BaseVBActivity
import com.win.mvvmhelper.base.BaseViewModel

/**
 * 作者　: hegaojian
 * 时间　: 2021/6/9
 * 描述　: 需要自定义修改什么就重写什么 具体方法可以 搜索 BaseIView 查看
 */
abstract class BaseActivity<VM : BaseViewModel, VB : ViewBinding> : BaseVBActivity<VM, VB>() {

    lateinit var mToolbar: TitleBar

    override fun getTitleBarView(): View? {
        val titleBarView = LayoutInflater.from(this).inflate(R.layout.layout_titlebar_view, null)
        mToolbar = titleBarView.findViewById(R.id.customToolBar)
        return titleBarView
    }

    override fun initImmersionBar() {
        //设置共同沉浸式样式
        ImmersionBar.with(this).statusBarColor(R.color.colorPrimary)
            .fitsSystemWindows(true)
            .statusBarDarkFont(false) //状态栏字体是深色，不写默认为亮色
            .init()
    }


}
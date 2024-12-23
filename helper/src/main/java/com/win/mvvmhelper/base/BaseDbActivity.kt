package com.win.mvvmhelper.base

import android.view.View
import androidx.databinding.ViewDataBinding

import com.win.mvvmhelper.ext.inflateBinding

/**
 * 作者　: hegaojian
 * 时间　: 2020/11/18
 * 描述　:
 */
abstract class BaseDbActivity<VM : BaseViewModel,DB: ViewDataBinding> : BaseVmActivity<VM>(),BaseIView {

    //使用了DataBinding 就不需要 layoutId了，因为 会从DB泛型 找到相关的view
    override val layoutId: Int = 0

    lateinit var mBind: DB

    /**
     * 创建DataBinding
     */
    override fun initViewDataBind(): View? {
        //利用反射 根据泛型得到 ViewDataBinding
        mBind = inflateBinding()

        mBind.lifecycleOwner = this
        return mBind.root
    }

}
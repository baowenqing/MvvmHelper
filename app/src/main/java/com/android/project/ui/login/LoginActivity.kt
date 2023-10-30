package com.android.project.ui.login

import android.os.Bundle
import com.android.project.R
import com.android.project.base.BaseActivity
import com.android.project.databinding.ActivityLoginBinding
import com.gyf.immersionbar.ImmersionBar
import com.win.mvvmhelper.ext.getStringExt
import com.win.mvvmhelper.ext.initMenu
import com.win.mvvmhelper.ext.isEmpty
import com.win.mvvmhelper.ext.nav
import com.win.mvvmhelper.ext.onClick
import com.win.mvvmhelper.ext.showDialogMessage
import com.win.mvvmhelper.ext.toast

/**
 * 作者　: hegaojian
 * 时间　: 2020/11/18
 * 描述　:
 */
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        //初始化toolbar
        mToolbar.initMenu("登录")

        with(mBind) {
            vm = mViewModel
            loginBtn.onClick {
                if (mBind.loginPhone.isEmpty()) {
                    showDialogMessage("手机号不能为空")
                    return@onClick
                }
                if (mBind.loginPwd.isEmpty()) {
                    showDialogMessage("密码不能为空")
                    return@onClick
                }
                mViewModel.login {
                    "登录成功啦！".toast()
//                    nav<MainActivity>()
                }
            }
        }


    }

    override fun initData() {

    }


    override fun initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.white)
            .fitsSystemWindows(true)
            .statusBarDarkFont(true) //状态栏字体是深色，不写默认为亮色
            .init()
    }

    override fun showToolBar(): Boolean {
        return false
    }


}
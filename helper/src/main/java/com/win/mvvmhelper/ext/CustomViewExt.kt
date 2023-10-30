package com.win.mvvmhelper.ext

import android.widget.ImageView
import androidx.core.view.isVisible

import com.blankj.utilcode.util.ActivityUtils
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.win.mvvmhelper.R


fun TitleBar.initMenu(
    titleStr: String? = "",
    backImg: Int = R.mipmap.icon_back,
    onBack: (() -> Unit)? = { ActivityUtils.getTopActivity().onBackPressed() },
    rightImg: Int = 0,
    onRight: (() -> Unit)? = null
): TitleBar {

    setTitle(titleStr)

    findViewById<ImageView>(R.id.iv_right).run {
        isVisible = rightImg != 0
        setImageResource(rightImg)
        onClick {
            onRight?.invoke()
        }
    }


    if (backImg != 0) {
        setLeftIcon(backImg)
    } else {
        setLeftIcon(null)
    }
    setOnTitleBarListener(object : OnTitleBarListener {
        override fun onLeftClick(titleBar: TitleBar?) {
            super.onLeftClick(titleBar)
            onBack?.invoke()
        }
    })

    return this
}


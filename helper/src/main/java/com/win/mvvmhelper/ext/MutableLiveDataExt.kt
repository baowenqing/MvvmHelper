package com.win.mvvmhelper.ext

import androidx.lifecycle.MutableLiveData

/**
 *  对 MutableLiveData主动刷新 通知xml更新
 *  示例：
 *
 *      val  user=MutableLiveData<User>
 *
 *       user.update{item->
 *            item.age=12
 *       }
 *
 */
inline fun <T> MutableLiveData<T>.update(exp: (data: T) -> Unit) {
    this.run {
        value = value?.also { item ->
            exp.invoke(item)
        }
    }
}

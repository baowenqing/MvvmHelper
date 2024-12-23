package com.android.project.app

import android.app.Application
import com.android.project.BuildConfig
import com.effective.android.anchors.AnchorsManager
import com.effective.android.anchors.task.project.Project
import com.win.mvvmhelper.base.MvvmHelper
import com.win.mvvmhelper.ext.currentProcessName

/**
 * 作者　: hegaojian
 * 时间　: 2021/6/9
 * 描述　:
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MvvmHelper.init(this, BuildConfig.DEBUG)
        val processName = currentProcessName
        if (currentProcessName == packageName) {
            // 主进程初始化
            onMainProcessInit()
        } else {
            // 其他进程初始化
            processName?.let { onOtherProcessInit(it) }
        }
    }

    /**
     * @description  代码的初始化请不要放在onCreate直接操作，按照下面新建异步方法
     */
    private fun onMainProcessInit() {
        AnchorsManager.getInstance()
            .debuggable(BuildConfig.DEBUG)
            //设置锚点
            .addAnchor(
                InitNetWork.Companion.TASK_ID,
                InitUtils.Companion.TASK_ID,
                InitComm.Companion.TASK_ID
            ).start(
                Project.Builder("app", AppTaskFactory())
                    .add(com.android.project.app.InitNetWork.Companion.TASK_ID)
                    .add(com.android.project.app.InitComm.Companion.TASK_ID)
                    .add(com.android.project.app.InitUtils.Companion.TASK_ID)
                    .build()
            )
    }

    /**
     * 其他进程初始化，[processName] 进程名
     */
    private fun onOtherProcessInit(processName: String) {}

}
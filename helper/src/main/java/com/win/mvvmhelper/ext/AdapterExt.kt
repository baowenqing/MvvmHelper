package com.win.mvvmhelper.ext

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.win.mvvmhelper.entity.BasePage
import com.win.mvvmhelper.net.LoadStatusEntity

/**
 * 作者　: hegaojian
 * 时间　: 2020/11/5
 * 描述　:
 */

/**
 * 下拉刷新
 * @receiver SmartRefreshLayout
 * @param refreshAction Function0<Unit>
 * @return SmartRefreshLayout
 */
fun SmartRefreshLayout.refresh(refreshAction: () -> Unit = {}): SmartRefreshLayout {
    this.setOnRefreshListener {
        refreshAction.invoke()
    }
    return this
}

/**
 * 上拉加载
 * @receiver SmartRefreshLayout
 * @param loadMoreAction Function0<Unit>
 * @return SmartRefreshLayout
 */
fun SmartRefreshLayout.loadMore(loadMoreAction: () -> Unit = {}): SmartRefreshLayout {
    this.setOnLoadMoreListener {
        loadMoreAction.invoke()
    }
    return this
}

/**
 * 列表数据加载成功
 * @receiver BaseQuickAdapter<T,*>
 * @param baseListNetEntity BaseListNetEntity<T>
 */
fun <T> BaseQuickAdapter<T, *>.loadListSuccess(baseListNetEntity: BasePage<T>, smartRefreshLayout: SmartRefreshLayout) {
    //关闭头部刷新
    if (baseListNetEntity.isRefresh()) {
        //如果是第一页 那么设置最新数据替换
        this.setNewInstance(baseListNetEntity.getPageData())
        smartRefreshLayout.finishRefresh()
    } else {
        //不是第一页，累加数据
        this.addData(baseListNetEntity.getPageData())
        //刷新一下分割线
        this.recyclerView.post { this.recyclerView.invalidateItemDecorations() }
    }
    //乳沟还有下一页数据 那么设置 smartRefreshLayout 还可以加载更多数据
    if (baseListNetEntity.hasMore()) {
        smartRefreshLayout.finishLoadMore()
        smartRefreshLayout.setNoMoreData(false)
    } else {
        //乳沟没有更多数据了，设置 smartRefreshLayout 加载完毕 没有更多数据
        smartRefreshLayout.finishLoadMoreWithNoMoreData()
    }
}

/**
 * 列表数据请求失败
 * @receiver BaseQuickAdapter<*, *>
 * @param loadStatus LoadStatusEntity
 * @param smartRefreshLayout SmartRefreshLayout
 */
fun BaseQuickAdapter<*, *>.loadListError(loadStatus: LoadStatusEntity, smartRefreshLayout: SmartRefreshLayout) {
    if (loadStatus.isRefresh) {
        //关闭头部刷新
        smartRefreshLayout.finishRefresh()
    } else {
        // 不是第一页请求，让recyclerview设置加载失败
        smartRefreshLayout.finishLoadMore(false)
    }
    //给个错误提示
    loadStatus.errorMessage.toast()
}


/**
 * 给adapter拓展的，防止重复点击item
 */
var adapterLastClickTime = 0L

fun BaseQuickAdapter<*, *>.setNbOnItemClickListener(
    interval: Long = 1000,
    action: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit
) {
    setOnItemClickListener { adapter, view, position ->
        val currentTime = System.currentTimeMillis()
        if (adapterLastClickTime != 0L && (currentTime - adapterLastClickTime < interval)) {
            return@setOnItemClickListener
        }
        adapterLastClickTime = currentTime
        action(adapter, view, position)
    }
}

/**
 * 给adapter拓展的，防止重复点击item
 */
var adapterChildLastClickTime = 0L
fun BaseQuickAdapter<*, *>.setNbOnItemChildClickListener(
    interval: Long = 1000,
    action: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit
) {
    setOnItemChildClickListener { adapter, view, position ->
        val currentTime = System.currentTimeMillis()
        if (adapterChildLastClickTime != 0L && (currentTime - adapterChildLastClickTime < interval)) {
            return@setOnItemChildClickListener
        }
        adapterChildLastClickTime = currentTime
        action(adapter, view, position)
    }
}

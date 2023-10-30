package com.win.mvvmhelper.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.SmartRefreshLayout.setDefaultRefreshFooterCreator
import com.scwang.smart.refresh.layout.SmartRefreshLayout.setDefaultRefreshHeaderCreator
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.win.mvvmhelper.R
import com.win.mvvmhelper.databinding.LayoutRefreshRvBinding
import com.win.mvvmhelper.entity.BasePage
import com.win.mvvmhelper.ext.loadListSuccess
import com.win.mvvmhelper.ext.vertical


/**
 * @author 文庆
 * @description 含下拉刷新 上拉加载的rv
 */
class RefreshRecycleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs), OnRefreshLoadMoreListener {


    //默认当前页 和每页的数量
    var page = 1
    var pageSize = 20


    private var emptyView: View
    private lateinit var mIvEmpty: ImageView
    private lateinit var mTvEmpty: TextView



    // 默认下拉刷新和加载更多 都有
    private var isRefreshEnable = true
    private var isLoadMoreEnable = true

    private lateinit var mOnRefresh: (refreshLayout: RefreshLayout) -> Unit
    private lateinit var mOnLoadMore: (refreshLayout: RefreshLayout) -> Unit


    private var mBinding: LayoutRefreshRvBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.layout_refresh_rv,
        this, true
    )

    init {
        mBinding.refreshLayout.run {
            //设置是否启用越界拖动（仿苹果效果）
            setEnableOverScrollDrag(true)
            // 设置是否在加载更多完成之后滚动内容显示新数据
            setEnableScrollContentWhenLoaded(true)
            setEnableScrollContentWhenRefreshed(true)
            //是否在全部加载结束之后Footer跟随内容
            setEnableFooterFollowWhenNoMoreData(true)
            //设置在内容不满一页的时候，是否可以上拉加载更多
            setEnableLoadMoreWhenContentNotFull(true)
            //是否启用列表惯性滑动到底部时自动加载更多
            setEnableAutoLoadMore(true);


            setDefaultRefreshHeaderCreator { context, layout ->
                ClassicsHeader(context)
            }
            setDefaultRefreshFooterCreator { context, layout ->
                ClassicsFooter(context)
            }
        }

        with(mBinding.rv) {
            emptyView =
                LayoutInflater.from(context).inflate(R.layout.layout_empty_view, null, false)

            mIvEmpty = emptyView.findViewById<ImageView>(R.id.iv_pic)
            mTvEmpty = emptyView.findViewById<TextView>(R.id.tv_text)

            //默认垂直布局
            vertical()
        }

    }

    //设置能否加载更多
    @SuppressLint("RestrictedApi")
    fun setLoadMoreEnable(enable: Boolean): RefreshRecycleView {
        isLoadMoreEnable = enable
        getRefreshLayout().setEnableLoadMore(enable)
        return this
    }

    fun setRefreshEnable(enable: Boolean): RefreshRecycleView {
        isRefreshEnable = enable
        getRefreshLayout().setEnableRefresh(enable)
        return this
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager?): RefreshRecycleView {
        if (getRv().layoutManager!!.itemCount != 0) {
            getRv().layoutManager!!.removeAllViews()
        }
        getRv().layoutManager = layoutManager
        return this
    }


    /**
     * 设置适配器（没有刷新和加载）
     *
     * @param adapter 自己处理数据源问题
     */
    fun <T> setAdapter(adapter: BaseQuickAdapter<T, out BaseViewHolder>) {
        setRefreshEnable(false)
        setLoadMoreEnable(false)

        getRv().setAdapter(adapter)
        adapter.setEmptyView(emptyView);
    }

    /**
     * 设置适配器（自己设置是否 刷新和加载）
     *
     * @param adapter 自己处理数据源问题
     */
    fun <T> setAdapter(
        adapter: BaseQuickAdapter<T, out BaseViewHolder>,
        onRefresh: (refreshLayout: RefreshLayout) -> Unit,
        onLoadMore: (refreshLayout: RefreshLayout) -> Unit
    ) {

        mOnRefresh = onRefresh
        mOnLoadMore = onLoadMore
        getRefreshLayout().setOnRefreshLoadMoreListener(this)

        adapter.setEmptyView(emptyView)
        getRv().adapter = adapter
    }




    override fun onRefresh(refreshLayout: RefreshLayout) {
        page = 1
        mOnRefresh.invoke(refreshLayout)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        page++
        mOnLoadMore.invoke(refreshLayout)
    }

    fun autoRefresh() {
        getRefreshLayout().run {
            finishRefresh()
            finishLoadMore()
            page = 1
            autoRefresh()
        }
    }

    /**
     * 自动处理数据
     *
     * @param list
     * @param <T>
     */
    fun <T> handleRecycleViewData(baseListNetEntity: BasePage<T>) {

        try {
            (getRv().adapter as BaseQuickAdapter<T, out BaseViewHolder>).run {
                val smartRefreshLayout: SmartRefreshLayout = getRefreshLayout()
                loadListSuccess(baseListNetEntity, smartRefreshLayout)
            }
        } catch (e: Exception) {
        }
    }


    fun getRv(): RecyclerView {
        return mBinding.rv
    }

    fun getRefreshLayout(): SmartRefreshLayout {
        return mBinding.refreshLayout
    }

}
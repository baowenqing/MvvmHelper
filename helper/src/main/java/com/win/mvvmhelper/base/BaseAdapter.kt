package com.win.mvvmhelper.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.win.mvvmhelper.R
import com.win.mvvmhelper.ext.setNbOnItemChildClickListener
import com.win.mvvmhelper.ext.setNbOnItemClickListener


abstract class BaseAdapter<T, K : ViewDataBinding> constructor(
    layoutResId: Int,
    data: MutableList<T>? = null,
    private val isHasEmptyLayout: Boolean = true,
) : BaseQuickAdapter<T, DataBindingHolder<K>>(layoutResId, data) {


    override fun convert(holder: DataBindingHolder<K>, item: T) {
        holder.dataBinding?.let {
            onUpdate(it, item, holder.adapterPosition)
            it.executePendingBindings()
        }
    }

    override fun addData(data: T) {
        this.data.add(data)
        notifyDataSetChanged()
    }

    override fun addData(position: Int, data: T) {
        this.data.add(position, data)
        notifyDataSetChanged()
    }

      fun removeData(position: Int) {
        this.data.removeAt(position)
        notifyDataSetChanged()
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        if (isHasEmptyLayout) {
            setEmptyView(R.layout.layout_empty_view)
        }
    }


    abstract fun onUpdate(binding: K, item: T, position: Int)


    /**
     *  isLimit  是否增加防止快速点击的限制
     */
    fun addOnItemClickListener(isLimit: Boolean = true, method: (item: T, position: Int) -> Unit) {
        if (isLimit) {
            setNbOnItemClickListener { adapter, view, position ->
                method.invoke(data[position], position)
            }
        } else {
            setOnItemClickListener { adapter, view, position ->
                method.invoke(data[position], position)
            }
        }


    }

    fun addOnItemChildClickListener(isLimit: Boolean = true,method: (item: T, view: View, position: Int) -> Unit) {
        if (isLimit) {
            setNbOnItemChildClickListener { adapter, view, position ->
                method.invoke(data[position], view, position)
            }
        }else{
            setOnItemChildClickListener { adapter, view, position ->
                method.invoke(data[position], view, position)
            }
        }
    }

    fun addOnItemLongClickListener(method: (item: T, position: Int) -> Unit) {
        setOnItemLongClickListener { adapter, view, position ->
            method.invoke(data[position], position)
            true
        }
    }

}

//  官方提供的BaseDataBindingHolder  取databind的时候 会有报错提示 但是不影响运行
//  链接： https://github.com/CymChad/BaseRecyclerViewAdapterHelper/issues/3475
class DataBindingHolder<BD : ViewDataBinding>(view: View) : BaseViewHolder(view) {

    val dataBinding by lazy {
        try {
            DataBindingUtil.bind<BD>(view)
        } catch (e: Exception) {
            null
        }
    }

}
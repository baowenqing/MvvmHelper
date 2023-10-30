package com.win.mvvmhelper.util.bindadapter

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.blankj.utilcode.util.CollectionUtils
import com.bumptech.glide.Glide


import com.ruffian.library.widget.RTextView
import com.ruffian.library.widget.iface.RHelper


object CustomBindAdapter {


    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun imageUrl(view: ImageView, url: Int?) {
        Glide.with(view.context.applicationContext)
            .load(url)
            .into(view)
    }

    @BindingAdapter(value = ["imageUrl"])
    @JvmStatic
    fun imageUrl(view: ImageView, url: String?) {
        Glide.with(view.context.applicationContext)
            .load(url.toString())
            .into(view)
    }






    @BindingAdapter(value = ["setText"])
    @JvmStatic
    fun setText(view: TextView, number: Int) {
        view.text = "$number"
    }

    @BindingAdapter(value = ["isVisible"])
    @JvmStatic
    fun isVisible(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }
    @BindingAdapter(value = ["isVisible"])
    @JvmStatic
    fun isVisibleByText(view: View,str:String?) {
        view.isVisible = !str.isNullOrBlank()
    }

    @BindingAdapter(value = ["isVisible"])
    @JvmStatic
    fun isVisibleByCollection(view: View, list:Collection<*>?) {
        view.isVisible = CollectionUtils.isNotEmpty(list)
    }

    @BindingAdapter(value = ["isInvisible"])
    @JvmStatic
    fun isInvisible(view: View, isInvisible: Boolean) {
        view.isInvisible = isInvisible
    }

    @BindingAdapter(value = ["textWrap"])
    @JvmStatic
    fun textWrap(view: View, value: Any?) {
        if (view is TextView) {
            view.text= value?.toString()?:""
        }
    }




    @BindingAdapter(value = ["background_normal"])
    @JvmStatic
    fun backgroundNormal(view: View, resId: Int) {
        if (view is RHelper<*>) {
            (view  ).helper.backgroundColorNormal = resId
        }
    }
    @BindingAdapter(value = ["text_color_normal"])
    @JvmStatic
    fun text_color_normal(view: View, resId: Int) {
        if (view is RTextView ) {
            (view  ). helper.textColorNormal = resId
        }
    }

    @BindingAdapter("onEditorActionDoneListener")
    @JvmStatic
    fun onEditorActionDoneListener(view: TextView, listener: OnEditorActionDoneListener?) {
        if (listener == null) {
            view.setOnEditorActionListener(null)
        } else {
            view.imeOptions = EditorInfo.IME_ACTION_DONE
            view.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    listener.onEditorActionDone(v)
                }
                false
            }
        }
    }

    public interface OnEditorActionDoneListener {
        fun onEditorActionDone(view: TextView)
    }
}
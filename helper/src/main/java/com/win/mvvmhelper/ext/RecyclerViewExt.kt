package com.win.mvvmhelper.ext

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxItemDecoration
import com.win.mvvmhelper.util.decoration.DefaultDecoration
import com.win.mvvmhelper.widget.MyFlexboxLayoutManager

fun RecyclerView.flex(size: Float=4f, @ColorInt color: Int = Color.TRANSPARENT): RecyclerView {
    layoutManager = MyFlexboxLayoutManager(this.context)

    try {
        val decoration = FlexboxItemDecoration(context)
        decoration.setOrientation(FlexboxItemDecoration.BOTH)

        decoration.setDrawable(GradientDrawable().apply {
            setSize(dp2px(size), dp2px(size))
            setColor(color)
        })

        try {
            if (itemDecorationCount != 0) {
                removeItemDecorationAt(0)
            }
        } catch (e: Exception) {
        }
        addItemDecoration(decoration)
    } catch (e: Exception) {

    }

    return this
}

/**
 * 纵向recyclerview
 * @receiver RecyclerView
 * @return RecyclerView
 */
fun RecyclerView.vertical(): RecyclerView {
    layoutManager = LinearLayoutManager(this.context)
    return this
}

/**
 * 横向 recyclerview
 * @receiver RecyclerView
 * @return RecyclerView
 */
fun RecyclerView.horizontal(): RecyclerView {
    layoutManager = LinearLayoutManager(this.context).apply {
        orientation = RecyclerView.HORIZONTAL
    }
    return this
}

/**
 * grid recyclerview
 * @receiver RecyclerView
 * @return RecyclerView
 */
fun RecyclerView.grid(count: Int): RecyclerView {
    layoutManager = GridLayoutManager(this.context, count)
    return this
}

/**
 * 配置万能分割线
 * @receiver RecyclerView
 * @param block [@kotlin.ExtensionFunctionType] Function1<DefaultDecoration, Unit>
 * @return RecyclerView
 */
fun RecyclerView.divider(block: DefaultDecoration.() -> Unit): RecyclerView {
    val itemDecoration = DefaultDecoration(context).apply(block)
    try {
        if (itemDecorationCount != 0) {
            removeItemDecorationAt(0)
        }
    } catch (e: Exception) {
    }
    addItemDecoration(itemDecoration)
    return this
}


/**

mBind.listRecyclerView.run {
    grid(4)
    divider {
    setColor(getColorExt(R.color.colorWhite))
    setDivider(5.dp)
    includeVisible = true
}
adapter = testAdapter
}

 */

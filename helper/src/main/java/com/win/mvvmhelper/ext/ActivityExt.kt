package com.win.mvvmhelper.ext

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.InputType
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.TimeUtils
import com.win.mvvmhelper.R
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder
import com.xuexiang.xui.widget.picker.widget.configure.TimePickerType
import java.io.Serializable
import java.util.Calendar
import java.util.Date


inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))

}

inline fun <reified T : Activity> Context.nav(vararg params: Pair<String, Any>) {
    startActivity(Intent(this, T::class.java).putExtras(*params))
}

/**
 *  [Intent]的扩展方法，用来批量put键值对
 *  示例：
 *  <pre>
 *      intent.putExtras(
 *          "Key1" to "Value",
 *          "Key2" to 123,
 *          "Key3" to false,
 *          "Key4" to arrayOf("4", "5", "6")
 *      )
 * </pre>
 *
 * @param params 键值对
 */
fun Intent.putExtras(vararg params: Pair<String, Any>): Intent {
    if (params.isEmpty()) return this
    params.forEach { (key, value) ->
        when (value) {
            is Int -> putExtra(key, value)
            is Byte -> putExtra(key, value)
            is Char -> putExtra(key, value)
            is Long -> putExtra(key, value)
            is Float -> putExtra(key, value)
            is Short -> putExtra(key, value)
            is Double -> putExtra(key, value)
            is Boolean -> putExtra(key, value)
            is Bundle -> putExtra(key, value)
            is String -> putExtra(key, value)
            is IntArray -> putExtra(key, value)
            is ByteArray -> putExtra(key, value)
            is CharArray -> putExtra(key, value)
            is LongArray -> putExtra(key, value)
            is FloatArray -> putExtra(key, value)
            is Parcelable -> putExtra(key, value)
            is ShortArray -> putExtra(key, value)
            is DoubleArray -> putExtra(key, value)
            is BooleanArray -> putExtra(key, value)
            is CharSequence -> putExtra(key, value)
            is Array<*> -> {
                when {
                    value.isArrayOf<String>() ->
                        putExtra(key, value as Array<String?>)
                    value.isArrayOf<Parcelable>() ->
                        putExtra(key, value as Array<Parcelable?>)
                    value.isArrayOf<CharSequence>() ->
                        putExtra(key, value as Array<CharSequence?>)
                    else -> putExtra(key, value)
                }
            }
            is Serializable -> putExtra(key, value)
        }
    }
    return this
}



inline fun <reified TARGET : Activity> Fragment.nav(
    vararg params: Pair<String, Any>
) = activity?.run {
    startActivity(Intent(this, TARGET::class.java).putExtras(*params))
}

inline fun <reified TARGET : Activity> Activity.startActivity(
    vararg params: Pair<String, Any>
) {
    startActivity(Intent(this, TARGET::class.java).putExtras(*params))
}


inline fun Context.showDatePicker(
    crossinline method: (date: String) -> Unit
) {
    val mDatePicker = TimePickerBuilder(this) { date: Date, v: View? ->
        val str = TimeUtils.date2String(date, "yyyy-MM-dd")
        method.invoke(str)
    }.setTimeSelectChangeListener { }.setTitleText("日期选择")
        .build()
    mDatePicker.show()
}

inline fun Context.showDatePicker(
    startDate: Calendar, endDate: Calendar,
    crossinline method: (date: String) -> Unit
) {
    val mDatePicker = TimePickerBuilder(this) { date: Date, v: View? ->
        val str = TimeUtils.date2String(date, "yyyy-MM-dd")
        method.invoke(str)
    }.setTimeSelectChangeListener { }
        .setTitleText("日期选择")
        .setRangDate(startDate, endDate)

        .build()
    mDatePicker.show()
    mDatePicker.setDate(Calendar.getInstance())
}


inline fun Context.showTimePicker(

    crossinline method: (time: String) -> Unit
) {
    val mDatePicker = TimePickerBuilder(this) { date: Date, v: View? ->
        val str = TimeUtils.date2String(date, "yyyy-MM-dd HH:mm:00")
        method.invoke(str)
    }.setTimeSelectChangeListener { }
        .setTitleText("时间选择")
        .setType(TimePickerType.DATE)
        .build()
    mDatePicker.show()
    mDatePicker.setDate(Calendar.getInstance())
}


/**
 * 类似系统的上下文菜单ContextMenu的Dialog
 */
inline fun <T> Context.showContextMenuDialog(
    menuList: List<T>,
    title: String = "选项",
    crossinline method: (item: T, position: Int) -> Unit
) {
    MaterialDialog.Builder(this)
        .title(title)
        .items(menuList)
        .itemsCallback { dialog: MaterialDialog?, itemView: View?, position: Int, text: CharSequence ->
            method.invoke(menuList[position], position)
        }
        .show()
}


//@JvmOverloads
//inline fun Context.showBottomSheetList(
//    list: List<String>,
//    title: String = "",
//    crossinline method: (text: String, position: Int) -> Unit
//) {
//    val pvOptions: OptionsPickerView<String> =
//        OptionsPickerBuilder(this) { v: View?, options1: Int, options2: Int, options3: Int ->
//            method(list[options1], options1)
//            false
//        }.apply {
//            setTitleText(title)
//            setOutSideCancelable(false)
//        }.build<String>()
//    pvOptions.setPicker(list)
//    pvOptions.show()
//
//
//}


//这里如果是T   因为UI需求 所以必须重写tostring()
@JvmOverloads
inline fun <T> Context.showBottomSheetList(
    list: List<T>,
    title: String = "",
    isDialog: Boolean = false,
    crossinline method: (item: T, position: Int) -> Unit
) {
    val pvOptions: OptionsPickerView<T> =
        OptionsPickerBuilder(this) { v: View?, options1: Int, options2: Int, options3: Int ->
            method(list[options1], options1)
            false
        }.apply {
            setTitleText(title)
            isDialog(isDialog)

            setOutSideCancelable(false)
        }.build<T>()
    pvOptions.setPicker(list)

    pvOptions.show()
}

inline fun Context.showMessageDialog(
    title: String,
    message: CharSequence,
    yesActionStr: String = "确定",
    noActionStr: String = "取消",
    crossinline yes: (dialog: Dialog) -> Unit = { dialog -> dialog.dismiss() },
    crossinline no: (dialog: Dialog) -> Unit = { dialog -> dialog.dismiss() },
    isAutoDismiss: Boolean = true,

    ) {
    val builder = MaterialDialog.Builder(this).title(title).content(message)
        .cancelable(false)
        .autoDismiss(isAutoDismiss)
        .positiveText(yesActionStr)
        .onPositive(MaterialDialog.SingleButtonCallback { dialog: MaterialDialog, which: DialogAction? ->
            yes.invoke(dialog)
        })

    builder.apply {
        noActionStr.isNotEmpty().yes {
            negativeText(noActionStr)
                .onNegative { dialog, which ->
                    no.invoke(dialog)
                }
        }
        show()
    }
}


inline fun Context.showInputDialog(
    message: String,
    yesActionStr: String = "确定",
    noActionStr: String = "取消",
    crossinline yes: (text: String, dialog: MaterialDialog) -> Unit,
    hintMessage: String = "",
    title: String = "提示",
    preText: String = "",//预填充
    inputType: Int = InputType.TYPE_CLASS_TEXT,//输入类型
    allowEmptyInput: Boolean = false,//是否允许输入空
) {
    MaterialDialog.Builder(
        this
    ).title(title)
        .content(message)
        .inputType(
            inputType
//            InputType.TYPE_CLASS_NUMBER
//                    or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        )
        .input(hintMessage, preText, allowEmptyInput) { dialog, input -> }
        .positiveText(yesActionStr)
        .onPositive(MaterialDialog.SingleButtonCallback { dialog: MaterialDialog, which: DialogAction? ->
            val text = dialog.inputEditText!!.text.toString().trim()
            yes.invoke(text, dialog)
        })
        .onNegative { dialog, which -> dialog.dismiss() }
        .negativeText(noActionStr)
        .autoDismiss(false)
        .cancelable(false)
        .showListener {

        }
        .show()
}







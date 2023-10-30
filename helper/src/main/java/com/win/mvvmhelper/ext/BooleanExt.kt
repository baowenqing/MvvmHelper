package com.win.mvvmhelper.ext


sealed class BooleanExt<out T>

class TransferData<T>(val data: T) : BooleanExt<T>()
object Otherwise : BooleanExt<Nothing>()

inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> =
    when {
        this -> TransferData(block.invoke())
        else -> Otherwise
    }

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
    when (this) {
        is Otherwise -> block()
        is TransferData -> data
    }
inline fun <T> BooleanExt<T>.no(block: () -> T): T =
    when (this) {
        is Otherwise -> block()
        is TransferData -> data
    }

/**
 *  类似三目运算符
 *
 *  exp 表达式
 *  yesCallback  表达式true的回调  取最后一行作为返回值
 *  noCallback   表达式false的回调  取最后一行作为返回值
 */
fun  <T>  expCallback(exp:Boolean, yesCallback:() -> T,  noCallback:() -> T )  : T
//                            =exp.yes(yesCallback).no (noCallback)
                            = if (exp) {
                                        yesCallback.invoke()
                                    }else{
                                        noCallback.invoke()
                                    }


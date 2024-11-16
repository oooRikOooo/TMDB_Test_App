package com.tmdbtestapp.common.utils

sealed class State<out T> {

    data class Loading<out T>(val data: T?) : State<T>()
    data class Successes<out T>(val data: T) : State<T>()
    data class Error<out T>(val errorModel: Throwable, val data: T?) : State<T>()

    companion object {
        fun <T> loading(data: T? = null) = Loading(data)
        fun <T> successes(data: T) = Successes(data)
        fun <T> error(throwable: Throwable? = null, data: T? = null) = Error(
            errorModel = throwable ?: Throwable("Unknown error"),
            data = data
        )
    }
}
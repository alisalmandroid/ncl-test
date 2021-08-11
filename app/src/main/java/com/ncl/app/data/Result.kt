package com.ncl.app.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Loading<out T : Any>(val loading: Boolean) : Result<T>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception, val code: Int) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading<*> -> "Loading[Loading=$loading]"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}
package com.taru.data.base.local

/**
 * Created by Niraj on 21-12-2022.
 */
sealed interface LocalResult<T> {

    class Success<T>(val data: T) : LocalResult<T>

    class Message<T>(val code: Int, val message: String?) : LocalResult<T>

    class Exception<T>(val throwable: Throwable): LocalResult<T>


}
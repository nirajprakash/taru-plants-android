package com.taru.data.base.remote

/**
 * Created by Niraj on 21-12-2022.
 */
sealed interface ApiResult<T> {

    class Success<T>(val data: T) : ApiResult<T>

    class Message<T>(val code: Int, val message: String?) : ApiResult<T>

    class Exception<T>(val throwable: Throwable): ApiResult<T>


}
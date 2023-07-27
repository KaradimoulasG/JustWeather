package com.example.justweather.common.extensions

import com.example.justweather.common.StatusCode
import com.example.justweather.data.networkResultHandling.NetworkError
import com.example.justweather.data.networkResultHandling.NetworkException
import com.example.justweather.data.networkResultHandling.NetworkResult
import com.example.justweather.data.networkResultHandling.NetworkSuccess
import retrofit2.HttpException
import retrofit2.Response

suspend fun <T : Any> handleApi(
    execute: suspend () -> Response<T>,
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkSuccess(body)
        } else {
            handleError(response.code(), execute)
        }
    } catch (e: HttpException) {
        handleError(e.code(), execute)
    } catch (e: Throwable) {
        NetworkException(e)
    }
}

suspend fun <T : Any> handleError(
    code: Int,
    execute: suspend () -> Response<T>,
) = when (code) {
    StatusCode.BadRequest -> NetworkError.BadRequest(execute())
    StatusCode.Unauthorized -> NetworkError.Unauthorized(execute())
    StatusCode.Forbidden -> NetworkError.Forbidden(execute())
    StatusCode.NotFound -> NetworkError.NotFound(execute())
    StatusCode.Locked -> NetworkError.Locked(execute())
    else -> NetworkError.Unknown(execute())
}

suspend fun <T : Any> NetworkResult<T>.onSuccess(
    executable: suspend (T) -> Unit,
): NetworkResult<T> = apply {
    if (this is NetworkSuccess<T>) executable(data)
}

suspend fun <T : Any> NetworkResult<T>.onError(
    executable: suspend (code: Int, message: String?) -> Unit,
): NetworkResult<T> = apply {
    if (this is NetworkError<T>) executable(code, message)
}

suspend fun <T : Any> NetworkResult<T>.onException(
    executable: suspend (e: Throwable) -> Unit,
): NetworkResult<T> = apply {
    if (this is NetworkException<T>) executable(e)
}

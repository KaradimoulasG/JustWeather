package com.example.core_domain.domain.data.networkResultHandling

import retrofit2.Response

sealed interface NetworkResult<T : Any>

class NetworkSuccess<T : Any>(val data: T) : NetworkResult<T>
class NetworkException<T : Any>(val e: Throwable) : NetworkResult<T>

sealed class NetworkError<T : Any>(val code: Int, val message: String?) : NetworkResult<T> {
    class BadRequest<T : Any>(private val response: Response<T>) : NetworkError<T>(response.code(), response.message())
    class Unauthorized<T : Any>(private val response: Response<T>) : NetworkError<T>(response.code(), response.message())
    class Forbidden<T : Any>(private val response: Response<T>) : NetworkError<T>(response.code(), response.message())
    class NotFound<T : Any>(private val response: Response<T>) : NetworkError<T>(response.code(), response.message())
    class Locked<T : Any>(private val response: Response<T>) : NetworkError<T>(response.code(), response.message())
    class Unknown<T : Any>(private val response: Response<T>) : NetworkError<T>(response.code(), response.message())
}
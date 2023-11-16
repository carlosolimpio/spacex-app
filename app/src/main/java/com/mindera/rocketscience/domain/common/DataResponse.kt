package com.mindera.rocketscience.domain.common

sealed class DataResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : DataResponse<T>()
    data class Error(val message: String) : DataResponse<Nothing>()
}

package gstv.sicredi.core.utils

import androidx.annotation.StringRes

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(
        val code: Int? = null,
        val error: String? = null,
        @StringRes val messageId: Int? = null
    ) :
        ResultWrapper<Nothing>()

    object ServerError : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
    object Aborted : ResultWrapper<Nothing>()
}
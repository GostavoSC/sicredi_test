package gstv.sicredi.core.utils

import android.util.Log
import gstv.sicredi.R
import kotlinx.coroutines.CancellationException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): ResultWrapper<T> = try {
    ResultWrapper.Success(apiCall.invoke())
} catch (throwable: Throwable) {
    throwable.toResultError()
}

fun Throwable.toResultError() = when (val exception = this) {
    is IOException -> ResultWrapper.NetworkError
    is HttpException -> when (exception.code()) {
        401 -> ResultWrapper.GenericError(code = 401, messageId = R.string.error_not_authorized)
        404 -> ResultWrapper.NetworkError
        in 500 until 600 -> ResultWrapper.ServerError
        else -> exception.toGenericError() ?: ResultWrapper.NetworkError
    }
    is CancellationException -> ResultWrapper.Aborted
    else -> throw exception
}

private fun HttpException.toGenericError(): ResultWrapper.GenericError? {
    val errorMessage = response().extractError()

    return if (errorMessage != null && errorMessage.isNotBlank()) ResultWrapper.GenericError(
        code(),
        errorMessage
    )
    else null
}

private fun <T> Response<T>?.extractError(): String? {
    val errorBody = this?.errorBody() ?: return null

    return try {
        val jsonTokener = JSONTokener(errorBody.string())
        when (val result = jsonTokener.nextValue()) {
            is String -> result
            is JSONArray -> result.getString(0)
            is JSONObject -> result.getString("message")
            else -> result.toString()
        }
    } catch (e: JSONException) {
        Log.e("WMS", "A request error occurred")
        errorBody.string()
    }
}

const val BASE_URL = "https://5f5a8f24d44d640016169133.mockapi.io/api/"
package com.jthl.morekmptwo.net

/**
 * @author wanglei
 * @date 2025/12/8 10:58
 * @Description：
 */
sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val code: Int, val message: String,val exception: Throwable? = null) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
}
// 扩展函数
fun <T> NetworkResult<T>.onSuccess(action: (T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) action(data)
    return this
}

fun <T> NetworkResult<T>.onError(action: (Int, String) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Error) action(code, message)
    return this
}
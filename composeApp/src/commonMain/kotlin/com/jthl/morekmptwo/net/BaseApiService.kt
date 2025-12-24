package com.jthl.morekmptwo.net

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.io.IOException

/**
 * @author wanglei
 * @date 2025/12/8 11:10
 * @Description：
 */
abstract class BaseApiService {
    protected val client = ApiClient.client

    /**
     * 高阶函数：安全地执行网络请求
     * @param apiCall: 真正执行 Ktor 请求的挂起函数块
     */
    protected suspend inline fun <reified T> safeRequest(
        crossinline apiCall: suspend () -> HttpResponse
    ): NetworkResult<T> {
        return try {
            // 1. 执行请求
            val response = apiCall()

            // 2. 判断 HTTP 状态码
            if (response.status.value in 200..299) {
                val data = response.body<T>()
                NetworkResult.Success(data)
            } else {
                // 3. 处理 HTTP 错误 (400, 404, 500 等)
                NetworkResult.Error(
                    code = response.status.value,
                    message = response.status.description
                )
            }
        } catch (e: RedirectResponseException) {
            // 3xx 错误
            NetworkResult.Error(e.response.status.value, e.message)
        } catch (e: ClientRequestException) {
            // 4xx 错误
            NetworkResult.Error(e.response.status.value, e.message)
        } catch (e: ServerResponseException) {
            // 5xx 错误
            NetworkResult.Error(e.response.status.value, e.message)
        } catch (e: IOException) {
            // 网络链接错误
            NetworkResult.Error(-1, "Network Error: Please check your connection")
        } catch (e: Exception) {
            // 其他未知错误
            NetworkResult.Error(-2, e.message ?: "Unknown Error")
        }
    }
}
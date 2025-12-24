package com.jthl.morekmptwo.net

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * @author wanglei
 * @date 2025/12/5 15:07
 * @Description：
 */
object ApiClient {
    val baseUrl ="https://wanandroid.com/" //解决Web跨域问题
//    val baseUrl = "http://127.0.0.1:8080/"
    val client: HttpClient by lazy {
        HttpClient(getHttpClientEngine()) {

            //1 JSON 序列化
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
            //2 日志记录
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            // 3 超时配置
            install(HttpTimeout) {
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }
            //默认请求配置
            defaultRequest {
                url(baseUrl)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}
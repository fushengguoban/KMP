package com.jthl.morekmptwo.net

import com.jthl.morekmptwo.data.Newdata
import com.jthl.morekmptwo.data.data
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * @author wanglei
 * @date 2025/12/8 11:11
 * @Description：
 */
object UserRepository:BaseApiService() {

    // 类似 OkHttp/Retrofit 的调用风格
    suspend fun getUser(page: Int): NetworkResult<Newdata> {
        return safeRequest {
            // 这里使用 Ktor 的 DSL 构建请求
            client.get("article/list/${page}/json") {
//                // 可以在这里针对特定请求添加参数
//                parameter("lang", "zh")
            }
//            client.get("/snippets") {
//
//            }
        }
    }

}
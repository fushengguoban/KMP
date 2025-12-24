package com.jthl.morekmptwo

import com.jthl.morekmptwo.data.data
import kotlinx.serialization.Serializable
import kotlin.js.ExperimentalJsExport
import kotlin.js.JsExport
import kotlin.js.JsModule
import kotlin.js.js
import kotlin.math.log
import kotlinx.browser.window
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.Promise

/**
 * @author wanglei
 * @date 2025/12/18 16:47
 * @Description：
 */

@Serializable
data class Token(
    val value: String,
    val expireTime: Long? = null
)

@Serializable
data class UserInfo(
    val username: String,
    val email: String,
    val token: String
)

@JsExport
fun saveToken(token: String) {
    alert("✅ Kotlin 收到 Token: $token")

    // 保存到浏览器 localStorage
    window.localStorage.setItem("user_token", token)

    alert("💾 Token 已保存到 localStorage")
}

@JsExport
fun getToken(): String? {
    val token = window.localStorage.getItem("user_token")
    alert("📤 返回 Token: $token")
    return token
}

object StorageManager {
    fun getUserConfig(): String? {
        // window.localStorage 对应 JS 的 localStorage
        return window.localStorage.getItem("user_config")
    }

    fun clearConfig() {
        window.localStorage.removeItem("user_config")
    }
}


external interface JsUser : JsAny {
    val name: String
}

@OptIn(ExperimentalWasmJsInterop::class)
external fun fetchUserData(userId:String): Promise<JsUser>




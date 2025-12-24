package com.jthl.morekmptwo

import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.definedExternally
import kotlin.js.js
import kotlinx.browser.window // 确保环境正确
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.js.JsAny
import kotlin.js.Promise

external fun alert(message: String)

@OptIn(ExperimentalWasmJsInterop::class)
external fun prompt(message: String, default: String = definedExternally): String?

external val window: Window

external interface Window {
    val navigator: Navigator
}

external interface Navigator {
    val userAgent: String
}

class WebPlatformService : PlatformService {
    override fun getDeviceId(): String {
        return window.navigator.userAgent
    }

    override fun showToast(message: String) {
        alert(message)
    }

}

actual fun getPlatformService(): PlatformService = WebPlatformService()
actual fun getPlatformName(): String {
    return "WEB"
}

@OptIn(ExperimentalWasmJsInterop::class)
actual suspend fun fetchPlatformUser(userId: String): String {
    val jsUser = fetchUserData(userId).await()
    return jsUser.name
}


suspend fun <T : JsAny> Promise<T>.await(): T = suspendCancellableCoroutine { cont ->
    this.then(
        onFulfilled = { result ->
            cont.resume(result)
            null // Promise 的 then 要求返回 JsAny?，这里返回 null
        },
        onRejected = { error ->
            cont.resumeWithException(RuntimeException(error.toString()))
            null
        }
    )
}

@OptIn(markerClass = [ExperimentalMultiplatform::class])
actual fun sendOtherInfoPage(message: String) {
    alert("走了！！！！")
    window.localStorage.setItem("message", message)
    alert("走了！！！！111")
    window.location.href = "../Hello Other.html"

}
package com.jthl.morekmptwo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToBrowserNavigation
import androidx.navigation.compose.rememberNavController
import kotlin.js.JsExport
import kotlin.js.js
import kotlinx.browser.document  // 必须导入这个包
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.events.Event
import org.w3c.dom.MessageEvent
import kotlinx.browser.window
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.JsAny
import kotlin.js.unsafeCast
import kotlin.js.toJsString

@OptIn(ExperimentalComposeUiApi::class, ExperimentalBrowserHistoryApi::class)
fun main() {

//    ComposeViewport {
//        MaterialTheme {
//            val navController = rememberNavController()
//            LaunchedEffect(navController) {
//                navController.bindToBrowserNavigation()
//            }
//            App(navController)
//        }
//
//        alert(StorageManager.getUserConfig() + "")
//    }
    exportToWindow(::initSignOutModule)

    //启动消息总线监听
    setupMessageBus()
}

@JsFun("(func) => { window.initSignOutModule = func; }")
external fun exportToWindow(func: () -> Unit)

@Composable
fun SignOutManagementScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("这是 KMP 界面")
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@JsExport
fun initSignOutModule() {
    alert("走到这里了、、、")
    val canvase = document.getElementById("kmp-view") as HTMLDivElement
    ComposeViewport(canvase) {
        SignOutManagementScreen()
    }
}

/**
 * 消息总线设置
 */
fun setupMessageBus() {
    window.addEventListener("message", { event ->
        val msgEvent = event as? MessageEvent

        // msgEvent?.data 的类型是 JsAny?
        val rawData = msgEvent?.data

        if (rawData != null) {
            // 此时 rawData 已经通过了 null 检查，符合 JsAny 类型要求
            val source = getStringProp(rawData, "source")

            if (source == "JS_SPA") {
                val action = getStringProp(rawData, "action") ?: "unknown"
//                val requestId = getStringProp(rawData, "requestId") ?: "11111"
                val requestId = "11111111"
                println("调试成功 - Action: $action, ID: $requestId")
                handleAction(action, requestId)
            }
        }
    })
}

external interface JsRequest : JsAny {
    var source: JsAny?
    var requestId: JsAny?
    var payload: JsAny?
    var action: JsAny?
    var error: JsAny?
}

/**
 * 【核心】业务分发函数：根据 action 执行逻辑
 * 你可以根据需要在这里不断增加 when 的分支
 */
fun handleAction(action: String, requestId: String) {
    println("这里是什么：$action----requestId:$requestId")
    try {
        // 如果 requestId 为空，直接拦截，防止后续回复时找不到目标
        if (requestId.isEmpty()) return

        val result: String = when (action) {
            "getKmpVersion" -> "1.0.0-wasm"
            "checkPermission" -> "true"
            else -> "unknown_action"
        }
        sendJsReply(requestId, result, null)
    } catch (e: Exception) {
        sendJsReply(requestId, null, e.message ?: "Unknown Error")
    }
}

/**
 * 辅助函数：将结果通过 postMessage 发回给原生 JS 项目
 */

fun createJsObject(): JsAny = js("{}")
fun getStringProp(obj: JsAny, key: String): String? =
    js("obj[key] != null ? String(obj[key]) : null")

fun setJsProp(obj: JsAny, key: String, value: JsAny?): Unit = js("obj[key] = value")

@OptIn(ExperimentalWasmJsInterop::class)
fun sendJsReply(requestId: String, payload: String?, error: String?) {
    println("jinlail ")
    val response = createJsObject()
    println("response--- ")
    // 所有的赋值都先通过 toJsString() 明确转换为 Wasm 认可的 JsString
    setJsProp(response, "source", "KMP_CORE".toJsString())
    println("response--ssss- ")
    setJsProp(response, "requestId", requestId.toJsString())
    setJsProp(response, "payload", payload?.toJsString())

//    error?.let {
//        setJsProp(response, "error", it.toJsString())
//    }

    println("准备发送了！")
    window.postMessage(response, "*")
}


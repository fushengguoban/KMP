package com.jthl.morekmptwo

/**
 * @author wanglei
 * @date 2025/12/5 9:06
 * @Description：
 */
interface PlatformService {
    //预期获取平台唯一的ID，需要使用各自的原生API
    fun getDeviceId(): String

    //显示各自的Toast
    fun showToast(message: String)
}

expect fun getPlatformService(): PlatformService

//定义一个期望函数，用来获取平台名称
expect fun getPlatformName(): String

@OptIn(ExperimentalMultiplatform::class)
expect suspend fun fetchPlatformUser(userId: String): String

@OptIn(ExperimentalMultiplatform::class)
expect fun sendOtherInfoPage(message: String)
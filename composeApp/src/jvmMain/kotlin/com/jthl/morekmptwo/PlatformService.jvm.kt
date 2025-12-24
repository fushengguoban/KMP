package com.jthl.morekmptwo

actual fun getPlatformService(): PlatformService {
    return object : PlatformService {
        override fun getDeviceId(): String {
            return ""
        }

        override fun showToast(message: String) {
            println(message)
        }
    }
}

actual fun getPlatformName(): String {
    return "JVM"
}
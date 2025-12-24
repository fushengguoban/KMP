package com.jthl.morekmptwo

actual fun getPlatformService(): PlatformService {
    return actualInstance?:error("PlatformService must be initialized in Android Application class.")
}

actual fun getPlatformName(): String {
    return "Android"
}
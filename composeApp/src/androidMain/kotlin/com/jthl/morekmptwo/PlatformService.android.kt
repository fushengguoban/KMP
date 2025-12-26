package com.jthl.morekmptwo

actual fun getPlatformService(): PlatformService {
    return actualInstance?:error("PlatformService must be initialized in Android Application class.")
}

actual fun getPlatformName(): String {
    return "Android"
}

@OptIn(markerClass = [ExperimentalMultiplatform::class])
actual suspend fun fetchPlatformUser(userId: String): String {
    return ""
}

@OptIn(markerClass = [ExperimentalMultiplatform::class])
actual fun sendOtherInfoPage(message: String) {
}
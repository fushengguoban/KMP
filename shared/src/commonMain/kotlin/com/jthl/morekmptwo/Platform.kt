package com.jthl.morekmptwo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
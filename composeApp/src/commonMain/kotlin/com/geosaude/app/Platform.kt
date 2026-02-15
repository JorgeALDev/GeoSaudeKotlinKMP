package com.geosaude.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
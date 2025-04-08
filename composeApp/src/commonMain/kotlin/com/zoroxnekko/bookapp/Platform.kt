package com.zoroxnekko.bookapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
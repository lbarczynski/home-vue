package dev.bapps.homevue

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package dev.bapps.homevue

internal interface Platform {
    val isDebug: Boolean

    companion object {
        val current: Platform
            get() = getPlatform()
    }
}

internal expect fun getPlatform(): Platform
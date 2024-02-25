package dev.bapps.homevue.core

import dev.bapps.homevue.core.platform.BuildConfig

internal class AndroidPlatform : Platform {
    override val isDebug: Boolean = BuildConfig.DEBUG
}

internal actual fun getPlatform(): Platform = AndroidPlatform()

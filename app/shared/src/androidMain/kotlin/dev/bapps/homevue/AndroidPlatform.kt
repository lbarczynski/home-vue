package dev.bapps.homevue

import org.jetbrains.compose.components.resources.BuildConfig

internal class AndroidPlatform : Platform {
    override val isDebug: Boolean
        get() = BuildConfig.DEBUG
}

internal actual fun getPlatform(): Platform = AndroidPlatform()
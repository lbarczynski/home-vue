package dev.bapps.homevue

import kotlin.experimental.ExperimentalNativeApi

internal class IosPlatform : Platform {
    @OptIn(ExperimentalNativeApi::class)
    override val isDebug: Boolean
        get() = kotlin.native.Platform.isDebugBinary

}

internal actual fun getPlatform(): Platform = IosPlatform()
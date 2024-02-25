package dev.bapps.homevue.core

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
internal class IosPlatform : Platform {
    override val isDebug: Boolean = kotlin.native.Platform.isDebugBinary
}

internal actual fun getPlatform(): Platform = IosPlatform()

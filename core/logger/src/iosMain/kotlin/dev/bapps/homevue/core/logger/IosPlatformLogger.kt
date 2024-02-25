package dev.bapps.homevue.core.logger

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSBundleErrorMaximum
import platform.Foundation.NSThread
import platform.darwin.OS_LOG_TYPE_DEBUG
import platform.darwin.OS_LOG_TYPE_DEFAULT
import platform.darwin.OS_LOG_TYPE_ERROR
import platform.darwin.OS_LOG_TYPE_FAULT
import platform.darwin.OS_LOG_TYPE_INFO
import platform.darwin.os_log_type_t


private class IosPlatformLogger(override val masterTag: String) : PlatformLogger {

    override fun log(level: LogLevel, message: String, error: Throwable?) {
        val tag = when {
            masterTag.isNotEmpty() -> "${masterTag}.${tag()}"
            else -> tag()
        }

        println(message(level, tag, message, error))
    }

    private fun message(level: LogLevel, tag: String, message: String, error: Throwable?): String {
        return buildString {
            append("[${level.name.uppercase()}] $tag: $message")
            error?.let { append(" $it") }
        }
    }

    private fun tag(): String {
        return NSThread.callStackSymbols
            .map { symbol -> symbol.toString() }
            .first { className -> !className.contains(LOGGER_PACKAGE_NAME) }
            .substringAfter(" ")
            .substringAfter(KOTLIN_FUNCTION_SYMBOL_PREFIX)
            .substringBefore(METHOD_SYMBOL_PREFIX)
            .substringBefore(LAMBDA_SYMBOL_PREFIX)
            .substringAfterLast(PACKAGE_DOT)
    }

    private companion object {
        const val LOGGER_PACKAGE_NAME = "dev.bapps.homevue.core.logger"
        const val KOTLIN_FUNCTION_SYMBOL_PREFIX = "kfun:"
        const val METHOD_SYMBOL_PREFIX = "#"
        const val LAMBDA_SYMBOL_PREFIX = "$"
        const val PACKAGE_DOT = "."
    }
}

internal actual fun platformLogger(masterTag: String): PlatformLogger {
    return IosPlatformLogger(masterTag)
}

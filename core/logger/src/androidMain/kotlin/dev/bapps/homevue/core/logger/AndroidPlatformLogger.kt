package dev.bapps.homevue.core.logger

import android.util.Log
import java.util.regex.Pattern

private class AndroidPlatformLogger(override val masterTag: String) : PlatformLogger {
    override fun log(level: LogLevel, message: String, error: Throwable?) {
        val tag = when {
            masterTag.isNotEmpty() -> "${masterTag}.${classTag()}"
            else -> classTag()
        }
        when (level) {
            LogLevel.Verbose -> Log.v(tag, message, error)
            LogLevel.Debug -> Log.d(tag, message, error)
            LogLevel.Info -> Log.i(tag, message, error)
            LogLevel.Warn -> Log.w(tag, message, error)
            LogLevel.Error -> Log.e(tag, message, error)
        }
    }

    private fun classTag(): String {
        val tag = Exception().stackTrace
            .map(StackTraceElement::getClassName)
            .first { className -> !className.startsWith(BuildConfig.LIBRARY_PACKAGE_NAME) }
            .substringAfterLast(delimiter = '.')

        val anonymousClassMatch = ANONYMOUS_CLASS.matcher(tag)
        return when {
            anonymousClassMatch.find() -> anonymousClassMatch.replaceAll("")
            else -> tag
        }
    }

    private companion object {
        private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
    }
}

internal actual fun platformLogger(masterTag: String): PlatformLogger {
    return AndroidPlatformLogger(masterTag)
}

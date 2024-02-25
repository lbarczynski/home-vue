package dev.bapps.homevue.core.logger

internal interface PlatformLogger {

    val masterTag: String
    fun log(level: LogLevel, message: String, error: Throwable? = null)
}

internal expect fun platformLogger(masterTag : String): PlatformLogger

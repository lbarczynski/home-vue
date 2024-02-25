package dev.bapps.homevue.core.logger

enum class LogLevel {
    Verbose,
    Debug,
    Info,
    Warn,
    Error
}

internal fun LogLevel.isAtLeast(logLevel: LogLevel): Boolean {
    return ordinal >= logLevel.ordinal
}

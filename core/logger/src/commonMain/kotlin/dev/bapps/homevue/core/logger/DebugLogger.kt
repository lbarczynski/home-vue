package dev.bapps.homevue.core.logger

class DebugLogger internal constructor(
    private val platformLogger: PlatformLogger,
    private val minimumLogLevel: LogLevel
) : Logger {

    constructor(
        masterTag: String = "",
        minimumLogLevel: LogLevel = LogLevel.Verbose
    ) : this(
        platformLogger = platformLogger(masterTag),
        minimumLogLevel = minimumLogLevel
    )

    override fun subLogger(subTag: String): Logger {
        val masterTag = "${platformLogger.masterTag}.$subTag"
        return DebugLogger(
            platformLogger = platformLogger(masterTag),
            minimumLogLevel = minimumLogLevel
        )
    }

    override fun log(level: LogLevel, message: String, error: Throwable?) {
        if (level.isAtLeast(minimumLogLevel)) {
            platformLogger.log(level, message, error)
        }
    }

    override fun log(tag: String, level: LogLevel, message: String, error: Throwable?) {
        if (level.isAtLeast(minimumLogLevel)) {
            platformLogger.log(level, message, error)
        }
    }
}
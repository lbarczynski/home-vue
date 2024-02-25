package dev.bapps.homevue.core.logger

internal class CombinedLogger(private val innerLoggers: List<Logger>) : Logger {
    override fun subLogger(subTag: String): Logger {
        return CombinedLogger(
            innerLoggers = innerLoggers
                .map { it.subLogger(subTag) }
        )
    }

    override fun log(level: LogLevel, message: String, error: Throwable?) {
        innerLoggers.forEach { logger ->
            logger.log(level, message, error)
        }
    }

    override fun log(tag: String, level: LogLevel, message: String, error: Throwable?) {
        innerLoggers.forEach { logger ->
            logger.log(tag, level, message, error)
        }
    }
}
package dev.bapps.homevue.core.logger

interface Logger {
    fun subLogger(subTag: String): Logger
    fun log(level: LogLevel, message: String, error: Throwable? = null)
    fun log(tag: String, level: LogLevel, message: String, error: Throwable? = null)

    fun verbose(message: String) {
        log(LogLevel.Verbose, message, error = null)
    }

    fun verbose(tag: String, message: String) {
        log(tag, LogLevel.Verbose, message, error = null)
    }

    fun debug(message: String) {
        log(LogLevel.Debug, message, error = null)
    }

    fun debug(tag: String, message: String) {
        log(tag, LogLevel.Debug, message, error = null)
    }

    fun info(message: String) {
        log(LogLevel.Info, message, error = null)
    }

    fun info(tag: String, message: String) {
        log(tag, LogLevel.Info, message, error = null)
    }

    fun warn(message: String) {
        log(LogLevel.Warn, message, error = null)
    }

    fun warn(tag: String, message: String) {
        log(tag, LogLevel.Warn, message, error = null)
    }

    fun error(message: String, error: Throwable? = null) {
        log(LogLevel.Error, message, error)
    }

    fun error(tag: String, message: String, error: Throwable? = null) {
        log(tag, LogLevel.Error, message, error)
    }

    companion object : Logger {

        private var instance: Logger? = null

        fun initialize(vararg loggers: Logger) {
            initialize(loggers.toList())
        }

        fun initialize(loggers: List<Logger>) {
            instance = CombinedLogger(loggers)
        }

        override fun subLogger(subTag: String): Logger {
            return instance?.subLogger(subTag) ?: CombinedLogger(emptyList())
        }

        override fun log(level: LogLevel, message: String, error: Throwable?) {
            instance?.log(level, message, error)
        }

        override fun log(tag: String, level: LogLevel, message: String, error: Throwable?) {
            instance?.log(tag, level, message, error)
        }

    }
}
